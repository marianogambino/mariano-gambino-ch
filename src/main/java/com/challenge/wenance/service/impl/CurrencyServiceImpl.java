package com.challenge.wenance.service.impl;

import com.challenge.wenance.dto.CurrencyPage;
import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.helper.CurrencyHelper;
import com.challenge.wenance.model.*;
import com.challenge.wenance.repository.CurrencyRepository;
import com.challenge.wenance.sequencer.service.DatabaseSequenceService;
import com.challenge.wenance.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DatabaseSequenceService databaseSequenceService;
    private CurrencyRepository repository;

    public CurrencyServiceImpl(CurrencyRepository repository, DatabaseSequenceService databaseSequenceService){
        this.repository = repository;
        this.databaseSequenceService = databaseSequenceService;
    }

    @Override
    public void saveCurrency(Currency currency) {

        try {
            formatCurrentDateAndPersistCurrency(currency);
        } catch (ParseException e) {
           log.error("Date Parse Error : {}" , e.getMessage());
           //throw new ParseException(e.getMessage(), e.getErrorOffset());
        }

    }

    private void formatCurrentDateAndPersistCurrency(Currency currency) throws ParseException {
        currency.setCreationDate( SIMPLE_DATE_FORMAT.parse(SIMPLE_DATE_FORMAT.format( new Date() ) ));
        currency.setId( this.databaseSequenceService.generateSequence( Currency.SEQUENCE_NAME ));
        this.repository.insert( currency );
    }

    @Override
    public Currency getCurrencyById(Long id) {
        Optional<Currency> currency = this.repository.findById( id );
        return currency.get();
    }

    @Override
    public CryptoCurrency getCurrency(String currencyType, Date date) {
        Currency currency = this.repository.findByCreationDate(date);
        return CurrencyHelper.getCrytoCurrency( CurrencyTypeEnum.fromString(currencyType.toLowerCase()), currency);
    }

    @Override
    public CryptoCurrencyGroup getAverageBetweenDates(Date starDate, Date endDate) {
        List<Currency> currencyStream = this.repository.findCurrenciesByCreationDateBetween(starDate, endDate);

        OptionalDouble purchasePriceBtCars = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                      .map( CryptoCurrencyGroup :: getBtcars)
                      .map( Btcars::getPurchase_price)
                      .mapToDouble(BigDecimal::doubleValue)
                      .average();


        OptionalDouble sellingPriceBtCars = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getBtcars)
                .map( Btcars::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble purchasePriceDaiars = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getDaiars)
                .map( Daiars::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble sellingPriceDaiars = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getDaiars)
                .map( Daiars::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble purchasePriceBtcdai = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getBtcdai)
                .map( Btcdai::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble sellingPriceBtcdai = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getBtcdai)
                .map( Btcdai::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble purchasePriceDaiusd = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getDaiusd)
                .map( Daiusd::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble sellingPriceDaiusd = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getDaiusd)
                .map( Daiusd::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble purchasePriceEthars = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getEthars)
                .map( Ethars::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble sellingPriceEthars = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getEthars)
                .map( Ethars::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble purchasePriceEthdai = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getEthdai)
                .map( Ethdai::getPurchase_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        OptionalDouble sellingPriceEthdai = currencyStream.stream().map( Currency::getCryptoCurrencyGroup )
                .map( CryptoCurrencyGroup :: getEthdai)
                .map( Ethdai::getSelling_price)
                .mapToDouble(BigDecimal::doubleValue)
                .average();

        return CryptoCurrencyGroup.builder()
                .btcars(
                    Btcars.builder()
                            .purchase_price( BigDecimal.valueOf( purchasePriceBtCars.getAsDouble()) )
                            .selling_price( BigDecimal.valueOf( sellingPriceBtCars.getAsDouble() ))
                            .build())
                .btcdai(
                        Btcdai.builder()
                                .purchase_price( BigDecimal.valueOf( purchasePriceBtcdai.getAsDouble()) )
                                .selling_price( BigDecimal.valueOf( sellingPriceBtcdai.getAsDouble() ))
                                .build())
                .daiars(
                        Daiars.builder()
                                .purchase_price( BigDecimal.valueOf( purchasePriceDaiars.getAsDouble()) )
                                .selling_price( BigDecimal.valueOf( sellingPriceDaiars.getAsDouble() ))
                                .build())
                .daiusd(
                        Daiusd.builder()
                                .purchase_price( BigDecimal.valueOf( purchasePriceDaiusd.getAsDouble()) )
                                .selling_price( BigDecimal.valueOf( sellingPriceDaiusd.getAsDouble() ))
                                .build())
                .ethars(
                        Ethars.builder()
                                .purchase_price( BigDecimal.valueOf( purchasePriceEthars.getAsDouble()) )
                                .selling_price( BigDecimal.valueOf( sellingPriceEthars.getAsDouble() ))
                                .build())
                .ethdai(
                        Ethdai.builder()
                                .purchase_price( BigDecimal.valueOf( purchasePriceEthdai.getAsDouble()) )
                                .selling_price( BigDecimal.valueOf( sellingPriceEthdai.getAsDouble() ))
                                .build())
                .build();


    }

    @Override
    public CurrencyPage getCurrencies(Date starDate, Date endDate, int page, int size ) {
        Sort sort = Sort.by(Sort.Order.desc("creationDate"));
        Pageable paging = PageRequest.of(page, size, sort);
        Optional<Date> starDateOptional = Optional.ofNullable(starDate);
        Optional<Date> endDateOptional = Optional.ofNullable(endDate);
        Page<Currency> currencyPage;
        if(starDateOptional.isPresent() && endDateOptional.isPresent()){
            currencyPage = this.repository.findCurrencyByCreationDateBetween(starDate, endDate, paging);
        }else {
            currencyPage = this.repository.findAll(paging);
        }

        return CurrencyPage.builder()
                .totalPages( currencyPage.getTotalPages() )
                .totalItems( currencyPage.getTotalElements())
                .currentPage( currencyPage.getNumber() )
                .currencies(currencyPage.getContent())
                .build();

    }


}
