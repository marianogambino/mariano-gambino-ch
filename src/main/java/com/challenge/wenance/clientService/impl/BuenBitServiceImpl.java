package com.challenge.wenance.clientService.impl;

import com.challenge.wenance.clientService.BuenBitService;
import com.challenge.wenance.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BuenBitServiceImpl implements BuenBitService {

    private static final String ENDPOINT =  "/market/tickers/";

    @Value("${buenbit.service.url}")
    private String buenBitUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<Currency> getCurrency() {
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory( buenBitUrl ) );
        Currency currencyResponse = restTemplate.getForObject( ENDPOINT , Currency.class);
        Optional<Currency> currency = Optional.ofNullable( currencyResponse );

        if( currency.isPresent() ) {
            Optional<List> errors = Optional.ofNullable( currency.get().getCryptoCurrencyGroup().getErrors() );

            if( errors.isPresent())
                log.info("Error when try to call to BuenBit service {}", errors.get() );

        }
        return currency;
    }
}
