package com.challenge.wenance.controller;

import com.challenge.wenance.dto.CurrencyPage;
import com.challenge.wenance.model.CryptoCurrency;
import com.challenge.wenance.model.Btcars;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RestController
@Slf4j
@Validated
@RequestMapping("api/cryptocurrency")
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @GetMapping(path="")
    @ResponseBody
    public CryptoCurrency getBtcArsPricing(
            @RequestParam(required = true, defaultValue = "btcars")   String btcType,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") Date currentTime){

        return this.currencyService.getCurrency( btcType, currentTime);
    }

    @GetMapping(path="/averagePrice")
    @ResponseBody
    public Currency getBtcPricingAverage(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") Date startTime,
                                               @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") Date endTime){

        return this.currencyService.getAvaregeBetweenDates(startTime, endTime);
    }

    @GetMapping(path="/allBtc")
    @ResponseBody
    public CurrencyPage getBtcPagination(@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") Date startTime,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") Date endTime,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size){

        return this.currencyService.getCurrencies(startTime, endTime, page, size);

    }

}
