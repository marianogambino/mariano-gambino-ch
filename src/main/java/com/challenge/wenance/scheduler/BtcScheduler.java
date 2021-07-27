package com.challenge.wenance.scheduler;


import com.challenge.wenance.service.FillingCurrencyDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class BtcScheduler {

    private FillingCurrencyDataService fillingCurrencyDataService;

    public BtcScheduler(FillingCurrencyDataService fillingCurrencyDataService){
        this.fillingCurrencyDataService = fillingCurrencyDataService;
    }

    @Scheduled(fixedDelay = 10000)
    public void callBuenBitService(){
        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        log.info("call buenbit service at : " + sdf.format(resultdate));
        this.fillingCurrencyDataService.getCurrencyDataAndPersist();
    }

}
