package com.challenge.wenance.clientService;

import com.challenge.wenance.model.Currency;

import java.util.Optional;


public interface BuenBitService {

    Optional<Currency> getCurrency();
}
