package com.challenge.wenance.service;

import com.challenge.wenance.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyAverageService<T> {

    T getAverage(List<Currency> currencies);

   /* Btcdai getAverageBtcdai(List<Currency> currencies);
    Daiars getAverageDaiars(List<Currency> currencies);
    Daiusd getAverageDaiusd(List<Currency> currencies);
    Ethdai getAverageEthdai(List<Currency> currencies);
    Ethars getAverageEthars(List<Currency> currencies);*/
}
