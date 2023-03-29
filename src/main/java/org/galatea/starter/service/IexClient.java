package org.galatea.starter.service;

import java.util.Date;
import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrices;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEX", url = "${spring.rest.iexBasePath}")
public interface IexClient {

  /**
   * Get a list of all stocks supported by IEX. See https://iextrading.com/developer/docs/#symbols.
   * As of July 2019 this returns almost 9,000 symbols, so maybe don't call it in a loop.
   *
   * @return a list of all of the stock symbols supported by IEX.
   */
  @GetMapping("/ref-data/symbols")
  List<IexSymbol> getAllSymbols(@RequestParam("token") String token);

  /**
   * Get the last traded price for each stock symbol passed in. See https://iextrading.com/developer/docs/#last.
   *
   * @param symbols stock symbols to get last traded price for.
   * @return a list of the last traded price for each of the symbols passed in.
   */
  @GetMapping("/tops/last")
  List<IexLastTradedPrice> getLastTradedPriceForSymbols(
      @RequestParam("symbols") String[] symbols,
      @RequestParam("token") String token);

  //todo:this calls the iex api?

  /**
   * Get the adjusted and unadjusted historical data for up to 15 years.
   *
   * @param symbols the list of symbols to get a last traded price for.
   * @param date a date object
   * @return Returns adjusted and unadjusted historical data for up to 15 years, and historical
   *      minute-by-minute intraday prices for the last 30 trailing calendar days.
   */
  @GetMapping("/data/CORE/HISTORICAL_PRICES/{symbols}?range=from{date}")
  List<IexHistoricalPrices> getHistoricalPrices(
      @RequestParam("symbols") String[] symbols,
      @RequestParam("date")
      @DateTimeFormat(pattern = "yyyyMMdd") Date date,
      @RequestParam("token") String token);

}
