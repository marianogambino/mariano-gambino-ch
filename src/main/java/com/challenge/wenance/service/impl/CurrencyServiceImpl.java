package com.challenge.wenance.service.impl;

import com.challenge.wenance.dto.CurrencyPage;
import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.helper.CurrencyHelper;
import com.challenge.wenance.model.CryptoCurrency;
import com.challenge.wenance.model.Currency;
import com.challenge.wenance.repository.CurrencyRepository;
import com.challenge.wenance.sequencer.service.DatabaseSequenceService;
import com.challenge.wenance.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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
    public Currency getAvaregeBetweenDates(Date starDate, Date endDate) {
        return null;
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
