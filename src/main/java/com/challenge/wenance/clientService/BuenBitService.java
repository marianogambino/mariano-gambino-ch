package com.challenge.wenance.clientService;

import com.challenge.wenance.model.Currency;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BuenBitService {

    Optional<Currency> getCurrency();
}
