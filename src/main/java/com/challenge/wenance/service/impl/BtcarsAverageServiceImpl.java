package com.challenge.wenance.service.impl;

import com.challenge.wenance.model.Btcars;
import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.model.Daiars;
import com.challenge.wenance.service.CurrencyAverageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class BtcarsAverageServiceImpl extends AverageServiceAbs implements CurrencyAverageService<Btcars> {

    @Override
    public Btcars getAverage(List<Currency> currencies) {
        OptionalDouble purchasePrice = getPurchasePrice(currencies);
        OptionalDouble sellingPrice = getSellingPrice(currencies);
        return  Btcars.builder()
                .purchase_price(BigDecimal.valueOf( purchasePrice.getAsDouble()))
                .selling_price( BigDecimal.valueOf( sellingPrice.getAsDouble() ))
                .build();
    }

    private OptionalDouble getPurchasePrice(List<Currency> currencies){
        return getStremCryptoCurrencyGroup(currencies)
                .map( CryptoCurrencyGroup:: getBtcars)
                .map( Btcars::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();
    }

    private OptionalDouble getSellingPrice(List<Currency> currencies){
        return getStremCryptoCurrencyGroup(currencies)
                .map( CryptoCurrencyGroup :: getBtcars)
                .map( Btcars::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();
    }
}
