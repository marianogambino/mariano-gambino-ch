package com.challenge.wenance.service.currency;

import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.factory.CurrencyAverageFactory;
import com.challenge.wenance.model.*;
import com.challenge.wenance.repository.CurrencyRepository;
import com.challenge.wenance.service.CurrencyService;
import com.challenge.wenance.service.impl.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CurrencyServiceTest {

    @TestConfiguration
    static class CurrencyServiceImplTestContextConfiguration {
        @Bean
        public CurrencyService currencyService() {
            return new CurrencyServiceImpl();
        }
    }

    @Autowired
    private CurrencyService currencyService;

    @MockBean
    private CurrencyRepository currencyRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void givenDate_whenGetCurrency_thenCurrencyBtcars(){
        Date currentDate = new Date();
        CryptoCurrencyGroup cryptoCurrencyGroup = CryptoCurrencyGroup.builder()
                .btcars(
                        Btcars.builder()
                                .ask_currency("ars")
                                .bid_currency("btc")
                                .currency("ars")
                                .purchase_price(BigDecimal.valueOf(6784600.0))
                                .selling_price(BigDecimal.valueOf(6920300.0))
                                .build()
                ).build();

        Currency currency = Currency.builder()
                .creationDate(currentDate)
                .id(1L)
                .cryptoCurrencyGroup(cryptoCurrencyGroup)
                .build();
        Mockito.when(this.currencyRepository.findByCreationDate(currentDate) )
                .thenReturn(currency);

        String btcType = CurrencyTypeEnum.BTCARS.getType();
        CryptoCurrency cryptoCurrency = this.currencyService.getCurrency( btcType, currentDate );

        Assert.assertTrue( Optional.ofNullable( cryptoCurrency).isPresent() );
        Assert.assertTrue( Optional.ofNullable( cryptoCurrency).get().getPurchase_price().compareTo( BigDecimal.valueOf(6784600.0)) == 0 );

    }

    @Test
    public void givenDate_whenGetAverageBetweenDates_thenCryptoCurrencyGroup(){
        Date startTime = new Date();
        Date endTime = new Date();
        CryptoCurrencyGroup cryptoCurrencyGroup = getCryptoCurrencyGroup();
        CryptoCurrencyGroup cryptoCurrencyGroup2 = getCryptoCurrencyGroup();

        Currency currency = Currency.builder()
                .creationDate(startTime)
                .id(1L)
                .cryptoCurrencyGroup(cryptoCurrencyGroup)
                .build();

        Currency currency2 = Currency.builder()
                .creationDate(endTime)
                .id(2L)
                .cryptoCurrencyGroup(cryptoCurrencyGroup2)
                .build();
        List<Currency> currencies = Arrays.asList(currency,currency2 );


        Mockito.when(this.currencyRepository.findCurrenciesByCreationDateBetween(startTime, endTime) )
                .thenReturn(currencies);

        CryptoCurrencyGroup response = this.currencyService.getAverageBetweenDates( startTime, endTime );
        Assert.assertTrue( Optional.ofNullable( response).isPresent() );
        Assert.assertTrue( Optional.ofNullable( response).get().getBtcars().getPurchase_price().compareTo( BigDecimal.valueOf(6784600.0)) == 0 );
    }

    @Test
    public void getCurrencies(){

    }

    private CryptoCurrencyGroup getCryptoCurrencyGroup(){
        return CryptoCurrencyGroup.builder()
                .btcars(
                        Btcars.builder()
                                .purchase_price(BigDecimal.valueOf(6784600.0))
                                .selling_price(BigDecimal.valueOf(6920300.0))
                                .build()
                )
                .btcdai(
                        Btcdai.builder()
                                .purchase_price(BigDecimal.valueOf(6784600.0))
                                .selling_price(BigDecimal.valueOf(6920300.0))
                                .build()
                )
                .daiars(
                        Daiars.builder()
                                .purchase_price(BigDecimal.valueOf(6784600.0))
                                .selling_price(BigDecimal.valueOf(6920300.0))
                                .build()
                )
                .daiusd(
                        Daiusd.builder()
                                .purchase_price(BigDecimal.valueOf(6784600.0))
                                .selling_price(BigDecimal.valueOf(6920300.0))
                                .build()
                ).ethars(
                        Ethars.builder()
                                .purchase_price(BigDecimal.valueOf(6784600.0))
                                .selling_price(BigDecimal.valueOf(6920300.0))
                                .build()
                )
                .ethdai(
                        Ethdai.builder()
                                .purchase_price(BigDecimal.valueOf(6784600.0))
                                .selling_price(BigDecimal.valueOf(6920300.0))
                                .build()
                )
                .build();
    }

}
