package com.challenge.wenance.service.impl;

import com.challenge.wenance.dto.CurrencyPage;
import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.factory.CurrencyAvarageFactory;
import com.challenge.wenance.helper.CurrencyHelper;
import com.challenge.wenance.model.*;
import com.challenge.wenance.repository.CurrencyRepository;
import com.challenge.wenance.sequencer.service.DatabaseSequenceService;
import com.challenge.wenance.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DatabaseSequenceService databaseSequenceService;
    @Autowired
    private CurrencyRepository repository;
    private CurrencyAvarageFactory currencyAvarageFactory;

    public CurrencyServiceImpl(CurrencyRepository repository,
                               DatabaseSequenceService databaseSequenceService,
                               CurrencyAvarageFactory currencyAvarageFactory){
        this.repository = repository;
        this.databaseSequenceService = databaseSequenceService;
        this.currencyAvarageFactory = currencyAvarageFactory;
    }

    public CurrencyServiceImpl() {
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
        Optional<Currency> optionalCurrency = Optional.ofNullable(currency);
        if(optionalCurrency.isPresent())
            return CurrencyHelper.getCrytoCurrency( CurrencyTypeEnum.fromString(currencyType.toLowerCase()), currency);

        return Btcars.builder().build();
    }

    @Override
    public CryptoCurrencyGroup getAverageBetweenDates(Date starDate, Date endDate) {
        List<Currency> allCurrenciesUpToday = this.repository.findCurrenciesByCreationDateBetween(starDate, endDate);

        Optional<List<Currency>> optionalCurrencies = Optional.ofNullable(allCurrenciesUpToday);
        if(optionalCurrencies.isPresent() && optionalCurrencies.get().size() > 0) {
            Btcars btcars = (Btcars) currencyAvarageFactory.getCurrencyServiceAverage(CurrencyTypeEnum.BTCARS).getAverage(allCurrenciesUpToday);
            Btcdai btcdai = (Btcdai) currencyAvarageFactory.getCurrencyServiceAverage(CurrencyTypeEnum.BTCDAI).getAverage(allCurrenciesUpToday);
            Daiars daiars = (Daiars) currencyAvarageFactory.getCurrencyServiceAverage(CurrencyTypeEnum.DAIARS).getAverage(allCurrenciesUpToday);
            Daiusd daiusd = (Daiusd) currencyAvarageFactory.getCurrencyServiceAverage(CurrencyTypeEnum.DAIUSD).getAverage(allCurrenciesUpToday);
            Ethars ethars = (Ethars) currencyAvarageFactory.getCurrencyServiceAverage(CurrencyTypeEnum.ETHARS).getAverage(allCurrenciesUpToday);
            Ethdai ethdai = (Ethdai) currencyAvarageFactory.getCurrencyServiceAverage(CurrencyTypeEnum.ETHDAI).getAverage(allCurrenciesUpToday);

            return CryptoCurrencyGroup.builder()
                    .btcars(btcars)
                    .btcdai(btcdai)
                    .daiusd(daiusd)
                    .daiars(daiars)
                    .ethars(ethars)
                    .ethdai(ethdai)
                    .build();
        }
        return CryptoCurrencyGroup.builder().build();
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

        Optional<Page<Currency>> optionalCurrencyPage = Optional.ofNullable(currencyPage);
        if(optionalCurrencyPage.isPresent()) {
            return CurrencyPage.builder()
                    .totalPages(currencyPage.getTotalPages())
                    .totalItems(currencyPage.getTotalElements())
                    .currentPage(currencyPage.getNumber())
                    .currencies(currencyPage.getContent())
                    .build();
        }
        return CurrencyPage.builder().build();

    }


}
