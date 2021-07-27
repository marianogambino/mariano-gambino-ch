package com.challenge.wenance.service.currency;

import com.challenge.wenance.dto.CurrencyPage;
import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.factory.CurrencyAverageFactory;
import com.challenge.wenance.model.*;
import com.challenge.wenance.model.Currency;
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
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

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
    public void givenStartDateAndEndDate_whenGetAverageBetweenDates_thenCryptoCurrencyGroup(){
        Date startTime = new Date();
        Date endTime = new Date();
        CryptoCurrencyGroup cryptoCurrencyGroup = getCryptoCurrencyGroup(6.0, 5.0);
        CryptoCurrencyGroup cryptoCurrencyGroup2 = getCryptoCurrencyGroup(2.0, 1.5);

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
        Assert.assertTrue( Optional.ofNullable( response).get()
                .getBtcars().getPurchase_price().compareTo( BigDecimal.valueOf(4.0)) == 0 );
    }

    @Test
    public void givenStartDateEndDate_whenGetCurrencies_thenCurrencyPage(){
        Date startTime = new Date();
        Date endTime = new Date();
        CryptoCurrencyGroup cryptoCurrencyGroup = getCryptoCurrencyGroup(6.0, 4.0);
        CryptoCurrencyGroup cryptoCurrencyGroup2 = getCryptoCurrencyGroup(5.0, 3.0);

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

        Sort sort = Sort.by(Sort.Order.desc("creationDate"));
        int page=1;
        int size=5;
        Pageable paging = PageRequest.of(page, size, sort);

        Page<Currency> currencyPage = getPageCurrency(currencies);

        Mockito.when(this.currencyRepository.findCurrencyByCreationDateBetween(startTime, endTime,paging) )
                .thenReturn(currencyPage);

        CurrencyPage response = this.currencyService.getCurrencies( startTime, endTime, page, size );
        Assert.assertTrue( Optional.ofNullable( response).isPresent() );
        Assert.assertTrue( response.getTotalItems() == 2 );
        Assert.assertTrue( response.getCurrentPage() == 1 );
        Assert.assertTrue( response.getCurrencies().get(0).getCryptoCurrencyGroup()
                .getBtcars().getPurchase_price().compareTo( BigDecimal.valueOf(6.0)) == 0);
    }

    @Test
    public void givenCallWihtoutDates_whenGetCurrencies_thenCurrencyPage(){
        Date startTime = new Date();
        Date endTime = new Date();
        CryptoCurrencyGroup cryptoCurrencyGroup = getCryptoCurrencyGroup(6.0, 4.0);
        CryptoCurrencyGroup cryptoCurrencyGroup2 = getCryptoCurrencyGroup( 2.0, 1.0);

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

        Sort sort = Sort.by(Sort.Order.desc("creationDate"));
        int page=1;
        int size=5;
        Pageable paging = PageRequest.of(page, size, sort);

        Page<Currency> currencyPage = getPageCurrency(currencies);

        Mockito.when(this.currencyRepository.findAll(paging) )
                .thenReturn(currencyPage);

        CurrencyPage response = this.currencyService.getCurrencies( null, null, page, size );
        Assert.assertTrue( Optional.ofNullable( response).isPresent() );
        Assert.assertTrue( response.getTotalItems() == 2 );
        Assert.assertTrue( response.getCurrentPage() == 1 );
        Assert.assertTrue( response.getCurrencies().get(0).getCryptoCurrencyGroup()
                .getBtcars().getPurchase_price().compareTo( BigDecimal.valueOf(6.0)) == 0);
    }

    private Page<Currency> getPageCurrency(List<Currency> currencies){
        Page<Currency> currencyPage = new Page<Currency>() {
            @Override
            public int getTotalPages() {
                return 2;
            }

            @Override
            public long getTotalElements() {
                return 2;
            }

            @Override
            public <U> Page<U> map(Function<? super Currency, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 1;
            }

            @Override
            public int getSize() {
                return 2;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Currency> getContent() {
                return currencies;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Currency> iterator() {
                return null;
            }
        };
        return currencyPage;
    }

    private CryptoCurrencyGroup getCryptoCurrencyGroup(double purchasePrice, double sellingPrice){
        return CryptoCurrencyGroup.builder()
                .btcars(
                        Btcars.builder()
                                .purchase_price(BigDecimal.valueOf(purchasePrice))
                                .selling_price(BigDecimal.valueOf(sellingPrice))
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
