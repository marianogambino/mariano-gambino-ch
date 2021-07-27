package com.challenge.wenance.service;

import com.challenge.wenance.dto.CurrencyPage;
import com.challenge.wenance.model.CryptoCurrency;
import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;

import java.util.Date;


public interface CurrencyService {

    void saveCurrency(Currency currency);
    Currency getCurrencyById(Long id);
    CryptoCurrency getCurrency(String currencyType, Date date);
    CryptoCurrencyGroup getAverageBetweenDates(Date starDate, Date endDate);
    CurrencyPage getCurrencies(Date starDate, Date endDate, int page, int size);
}
