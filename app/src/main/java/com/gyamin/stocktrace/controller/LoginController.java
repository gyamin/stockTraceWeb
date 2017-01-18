package com.gyamin.stocktrace.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gyamin.stocktrace.exception.ApplicationException;
import com.gyamin.stocktrace.exception.ValidateException;
import com.gyamin.stocktrace.bean.ErrorResponse;
import com.gyamin.stocktrace.service.LoginService;
import com.gyamin.web.session.SessionManager;
import com.gyamin.stocktrace.entity.Users;
import com.gyamin.stocktrace.request.LoginRequest;

@Controller
public class LoginController {

    /**
     * ログイン画面表示
     * @return
     */
    @RequestMapping(value = "/login", method = GET)
    public String loginIndex() {
        return "login/index";
    }

    /**
     * ログイン処理
     * @param request
     * @param httpServletRequest
     * @param bindingResult
     * @return
     * @throws ValidateException
     */
    @RequestMapping(value = "/login", method = POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    @ResponseBody
    public Object login(@RequestBody @Valid LoginRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws ApplicationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // 引数にHttpServletRequestを設定すると、HttpServletRequestを取得できる
        System.out.println(httpServletRequest.getMethod());     // POST
        // リクエストバリデーション
        if(bindingResult.hasErrors()) {
            ValidateException validateException = new ValidateException("バリデーションエラー");
            validateException.errors = bindingResult.getFieldErrors();
            throw validateException;
        }
        // userId, passwordに該当するユーザが存在するか確認する
        LoginService service = new LoginService();
        Map sessionInfo = service.doLogin(request);

        return new ResponseEntity<Users>((MultiValueMap<String, String>) sessionInfo, HttpStatus.OK);
    }

    /**
     * アプリケーションエラー時レスポンス処理
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ApplicationException.class })
    @ResponseBody
    public ErrorResponse applicatinError(ApplicationException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        return errorResponse;
    }

    /**
     * バリデーションエラー時レスポンス処理
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ValidateException.class })
    @ResponseBody
    public ErrorResponse validateError(ValidateException exception) {
        ErrorResponse errorResponse = new ErrorResponse();

        List<Map<String,Object>> errorList = new ArrayList();
        for(ObjectError error : exception.errors ) {
            Map<String, Object> info = new HashMap<String, Object>();
            info.put("field", ((FieldError) error).getField());
            info.put("message", ((FieldError) error).getDefaultMessage());
            errorList.add(info);
        }
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setFieldErrors(errorList);
        return errorResponse;
    }
}
