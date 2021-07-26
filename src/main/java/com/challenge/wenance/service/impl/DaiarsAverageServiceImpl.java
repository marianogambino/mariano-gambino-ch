package com.challenge.wenance.service.impl;

import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.model.Daiars;
import com.challenge.wenance.service.CurrencyAverageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class DaiarsAverageServiceImpl implements CurrencyAverageService<Daiars> {

    @Override
    public Daiars getAverage(List<Currency> currencies) {
        OptionalDouble purchasePriceBtCars = currencies.stream()
                .map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup:: getDaiars)
                .map( Daiars::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();


        OptionalDouble sellingPriceBtCars = currencies.stream()
                .map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getDaiars)
                .map( Daiars::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        return  Daiars.builder()
                .purchase_price(BigDecimal.valueOf( purchasePriceBtCars.getAsDouble()))
                .selling_price( BigDecimal.valueOf( sellingPriceBtCars.getAsDouble() ))
                .build();
    }
}
