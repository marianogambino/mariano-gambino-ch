package com.challenge.wenance.repository;

import com.challenge.wenance.model.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CurrencyRepository extends MongoRepository<Currency, Long> {

    @Query("{'creationDate' : { $gte: ?0, $lte: ?1 } }")
    List<Currency> findCurrenciesByCreationDateBetween(Date startDate, Date endDate);

    @Query("{'creationDate' : { $eq: ?0 } }")
    Currency findByCreationDate(Date creationDate);

    @Query("{'creationDate' : { $gte: ?0, $lte: ?1 } }")
    Page<Currency> findCurrencyByCreationDateBetween(Date startDate, Date endDate, Pageable pageable);

}
