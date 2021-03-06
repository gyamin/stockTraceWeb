package com.gyamin.stocktrace.dao;

import com.gyamin.stocktrace.AppConfig;
import com.gyamin.stocktrace.entity.StockPrices;
import java.time.LocalDate;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao(config = AppConfig.class)
public interface StockPricesDao {

    /**
     * @param tradeDate
     * @param issueCode
     * @return the StockPrices entity
     */
    @Select
    StockPrices selectById(LocalDate tradeDate, Integer issueCode);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(StockPrices entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(StockPrices entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(StockPrices entity);
}