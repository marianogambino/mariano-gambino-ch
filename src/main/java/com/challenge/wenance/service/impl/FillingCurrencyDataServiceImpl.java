package com.challenge.wenance.service.impl;

import com.challenge.wenance.clientService.BuenBitService;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.service.CurrencyService;
import com.challenge.wenance.service.FillingCurrencyDataService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FillingCurrencyDataServiceImpl implements FillingCurrencyDataService {

    private CurrencyService currencyService;
    private BuenBitService buenBitService;

    public FillingCurrencyDataServiceImpl(CurrencyService currencyService, BuenBitService buenBitService){
        this.currencyService = currencyService;
        this.buenBitService = buenBitService;

    }

    @Override
    public void getCurrencyDataAndPersist() {

        Optional<Currency> currencyResponse = this.buenBitService.getCurrency();
        if(currencyResponse.isPresent()){
            this.currencyService.saveCurrency( currencyResponse.get() );
        }
    }

}
