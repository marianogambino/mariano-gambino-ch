package com.challenge.wenance.service.currency;

import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.model.Btcars;
import com.challenge.wenance.model.CryptoCurrency;
import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.repository.CurrencyRepository;
import com.challenge.wenance.service.CurrencyService;
import com.challenge.wenance.service.impl.CurrencyServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;
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

    private Date currentDate = new Date();

    @Test
    public void givenDate_whenGetCurrency_thenCurrencyBtcars(){

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
    public void getAverageBetweenDates(){

    }

    @Test
    public void getCurrencies(){

    }

}
