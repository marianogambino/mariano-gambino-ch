package com.challenge.wenance.service;

import com.challenge.wenance.model.*;


import java.util.List;


public interface CurrencyAverageService<T> {

    T getAverage(List<Currency> currencies);


}
