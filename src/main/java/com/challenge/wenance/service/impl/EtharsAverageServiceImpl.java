package com.challenge.wenance.service.impl;

import com.challenge.wenance.model.Btcdai;
import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.model.Ethars;
import com.challenge.wenance.service.CurrencyAverageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class EtharsAverageServiceImpl extends AverageServiceAbs implements CurrencyAverageService<Ethars> {

    @Override
    public Ethars getAverage(List<Currency> currencies) {
        OptionalDouble purchasePrice = getPurchasePrice(currencies);
        OptionalDouble sellingPrice = getSellingPrice(currencies);

        return Ethars.builder()
                .purchase_price(BigDecimal.valueOf(purchasePrice.getAsDouble()))
                .selling_price(BigDecimal.valueOf(sellingPrice.getAsDouble()))
                .build();
    }

    private OptionalDouble getPurchasePrice(List<Currency> currencies){
        return getStremCryptoCurrencyGroup(currencies)
                .map(CryptoCurrencyGroup::getEthars)
                .map(Ethars::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();
    }

    private OptionalDouble getSellingPrice(List<Currency> currencies){
        return getStremCryptoCurrencyGroup(currencies)
                .map(CryptoCurrencyGroup::getEthars)
                .map(Ethars::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();
    }
}
