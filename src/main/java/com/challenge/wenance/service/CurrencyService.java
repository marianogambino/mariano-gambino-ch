package com.challenge.wenance.service;

import com.challenge.wenance.dto.CurrencyPage;
import com.challenge.wenance.model.CryptoCurrency;
import com.challenge.wenance.model.Currency;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public interface CurrencyService {

    void saveCurrency(Currency currency);
    Currency getCurrencyById(Long id);
    CryptoCurrency getCurrency(String currencyType, Date date);
    Currency getAvaregeBetweenDates(Date starDate, Date endDate);
    CurrencyPage getCurrencies(Date starDate, Date endDate, int page, int size);
}
