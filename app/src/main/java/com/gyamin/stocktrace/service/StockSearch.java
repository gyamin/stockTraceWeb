package com.gyamin.stocktrace.service;

import com.gyamin.stocktrace.dao.StockPricesDaoImpl;
import org.springframework.stereotype.Service;

import com.gyamin.stocktrace.dao.StockPricesDao;

import java.time.LocalDate;

@Service
public class StockSearch {

    private final StockPricesDao dao = new StockPricesDaoImpl();

//    public StockPrices selectByPrimaryKey() {
//
//        // データベース設定を取得
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-postgre.xml");
//        // bean設定を取得しインスタンス作成
//        StockPricesDao stockPricesDao = ctx.getBean(StockPricesDao.class);
//
//        // stockPricesDaoを利用してDBアクセス、株価価格情報を取得
//        StockPrices stockPrices = dao.selectById(LocalDate.of(2015, 11, 13), 1333);
//
//        return stockPrices;
//    }
}