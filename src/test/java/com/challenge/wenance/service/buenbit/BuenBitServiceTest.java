package com.challenge.wenance.service.buenbit;

import com.challenge.wenance.clientService.BuenBitService;
import com.challenge.wenance.model.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class BuenBitServiceTest {


    @Autowired
    private BuenBitService buenBitService;

    @Test
    public void consumeService(){
        Optional<Currency> currency = buenBitService.getCurrency();
        Assertions.assertTrue( currency.isPresent() );
        Assertions.assertEquals( currency.get().getCryptoCurrencyGroup().getBtcars().getAsk_currency(), "ars");
    }


}
