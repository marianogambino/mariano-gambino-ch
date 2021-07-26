package com.challenge.wenance.service.impl;

import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.model.Ethars;
import com.challenge.wenance.service.CurrencyAverageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class EtharsAverageServiceImpl implements CurrencyAverageService<Ethars> {

    @Override
    public Ethars getAverage(List<Currency> currencies) {
        OptionalDouble purchasePriceBtCars = currencies.stream()
                .map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup:: getEthars)
                .map( Ethars::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();


        OptionalDouble sellingPriceBtCars = currencies.stream()
                .map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getEthars)
                .map( Ethars::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        return  Ethars.builder()
                .purchase_price(BigDecimal.valueOf( purchasePriceBtCars.getAsDouble()))
                .selling_price( BigDecimal.valueOf( sellingPriceBtCars.getAsDouble() ))
                .build();
    }
}
