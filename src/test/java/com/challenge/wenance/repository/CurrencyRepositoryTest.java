package com.challenge.wenance.repository;

import com.challenge.wenance.model.CryptoCurrencyGroup;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.model.Daiars;
import com.challenge.wenance.repository.CurrencyRepository;
import com.challenge.wenance.sequencer.service.DatabaseSequenceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository repository;

    @Autowired
    private DatabaseSequenceService databaseSequenceService;

    @Test
    public void saveEntity(){

        CryptoCurrencyGroup cryptoCurrencyGroup = CryptoCurrencyGroup.builder().daiars(
                Daiars.builder()
                        .currency("ars")
                        .bid_currency("dai")
                        .ask_currency("ars").purchase_price( BigDecimal.valueOf(176.3) )
                        .selling_price(BigDecimal.valueOf(178.9) )
                        .market_identifier( "daiars")
                        .build())
                .build();

        Currency currency = Currency.builder()
                .id( databaseSequenceService.generateSequence( Currency.SEQUENCE_NAME ) )
                .cryptoCurrencyGroup(cryptoCurrencyGroup)
                .creationDate(new Date()).build();


        repository.insert(currency);

        Optional<Currency> currencyOptional = repository.findById( currency.getId() );

        Assertions.assertTrue(
                currencyOptional.get().getCryptoCurrencyGroup().getDaiars().getPurchase_price()
                        .compareTo( BigDecimal.valueOf(176.3) ) == 0);

    }

    @Test
    public void findById(){
        Optional<Currency> currencyOptional = repository.findById(490L );
        Currency currency = currencyOptional.get();
        Assertions.assertTrue( currency.getCryptoCurrencyGroup().getBtcars().getAsk_currency().equalsIgnoreCase( "ars"));
    }

    @Test
    public void findCurrencyByCreationDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataString = "2021-07-26 02:09:45";
        Date creationDate = sdf.parse( dataString );
        Currency currency = this.repository.findByCreationDate(creationDate);
        Assertions.assertTrue( currency.getCryptoCurrencyGroup().getBtcars().getAsk_currency().equalsIgnoreCase( "ars"));

    }

    @Test
    public void findCurrenciesByCreationDateBetween() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateString = "2021-07-26 01:00:00";
        Date startDate = sdf.parse( startDateString );

        String endDateString = "2021-07-26 02:55:20";
        Date endDate = sdf.parse( endDateString );

        List<Currency> currencyStream = this.repository.findCurrenciesByCreationDateBetween(startDate, endDate);
        //List<Currency> currencyList = currencyStream.collect(Collectors.toList());
        Assertions.assertTrue( currencyStream.isEmpty() == false);
        Assertions.assertTrue( currencyStream.get(0).getCryptoCurrencyGroup().getBtcars().getAsk_currency().equalsIgnoreCase( "ars"));

    }

    @Test
    public void findAllWithPagination(){
        Sort sort = Sort.by(Sort.Order.asc("creationDate"));
        Pageable paging = PageRequest.of(1, 10, sort);
        Page<Currency> page = this.repository.findAll(paging);
        List<Currency> currencyList = page.getContent();
        Assertions.assertTrue( currencyList.isEmpty() == false);
        Assertions.assertTrue( currencyList.size() == 10);
    }

    @Test
    public void findCurrencyByCreationDateBetween() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateString = "2021-07-26 00:00:00";
        Date startDate = sdf.parse( startDateString );

        String endDateString = "2021-07-26 00:55:00";
        Date endDate = sdf.parse( endDateString );

        Sort sort = Sort.by(Sort.Order.asc("creationDate"));
        Pageable paging = PageRequest.of(1, 10, sort);

        Page<Currency> page = this.repository.findCurrencyByCreationDateBetween(startDate, endDate, paging);

        Assertions.assertTrue( page.getContent().isEmpty() == false);
        Assertions.assertTrue( page.getContent().get(0).getCryptoCurrencyGroup().getBtcars().getAsk_currency().equalsIgnoreCase( "ars"));

    }


}
