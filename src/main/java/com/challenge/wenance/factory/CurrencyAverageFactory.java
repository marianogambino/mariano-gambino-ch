package com.challenge.wenance.factory;

import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.model.*;
import com.challenge.wenance.service.CurrencyAverageService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyAverageFactory {

    private CurrencyAverageService<Btcars> btcarsCurrencyAverageService;
    private CurrencyAverageService<Btcdai> btcdaiCurrencyAverageService;
    private CurrencyAverageService<Daiars> daiarsCurrencyAverageService;
    private CurrencyAverageService<Daiusd> daiusdCurrencyAverageService;
    private CurrencyAverageService<Ethars> etharsCurrencyAverageService;
    private CurrencyAverageService<Ethdai> ethdaiCurrencyAverageService;
    private Map<CurrencyTypeEnum, CurrencyAverageService> factory = new HashMap<>();

    public CurrencyAverageFactory(CurrencyAverageService<Btcars> btcarsCurrencyAverageService,
                                  CurrencyAverageService<Btcdai> btcdaiCurrencyAverageService,
                                  CurrencyAverageService<Daiars> daiarsCurrencyAverageService,
                                  CurrencyAverageService<Daiusd> daiusdCurrencyAverageService,
                                  CurrencyAverageService<Ethars> etharsCurrencyAverageService,
                                  CurrencyAverageService<Ethdai> ethdaiCurrencyAverageService) {
        this.btcarsCurrencyAverageService = btcarsCurrencyAverageService;
        this.btcdaiCurrencyAverageService = btcdaiCurrencyAverageService;
        this.daiarsCurrencyAverageService = daiarsCurrencyAverageService;
        this.daiusdCurrencyAverageService = daiusdCurrencyAverageService;
        this.etharsCurrencyAverageService = etharsCurrencyAverageService;
        this.ethdaiCurrencyAverageService = ethdaiCurrencyAverageService;

        factory.put(CurrencyTypeEnum.BTCARS, this.btcarsCurrencyAverageService);
        factory.put(CurrencyTypeEnum.BTCDAI, this.btcdaiCurrencyAverageService);
        factory.put(CurrencyTypeEnum.DAIARS, this.daiarsCurrencyAverageService);
        factory.put(CurrencyTypeEnum.DAIUSD, this.daiusdCurrencyAverageService);
        factory.put(CurrencyTypeEnum.ETHARS, this.etharsCurrencyAverageService);
        factory.put(CurrencyTypeEnum.ETHDAI, this.ethdaiCurrencyAverageService);
    }

    public CurrencyAverageService getCurrencyServiceAverage(CurrencyTypeEnum currencyTypeEnum){
        return this.factory.get(currencyTypeEnum);
    }
}
