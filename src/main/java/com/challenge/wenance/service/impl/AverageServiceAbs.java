package com.challenge.wenance.service.impl;

import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;

import java.util.List;
import java.util.stream.Stream;

public abstract class AverageServiceAbs {

    protected Stream<CryptoCurrencyGroup> getStremCryptoCurrencyGroup(List<Currency> currencies){
        return  currencies.stream()
                .map( Currency::getCryptoCurrencyGroup );
    }
}
