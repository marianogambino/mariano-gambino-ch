package com.challenge.wenance.helper;

import com.challenge.wenance.enums.CurrencyTypeEnum;
import com.challenge.wenance.model.CryptoCurrency;
import com.challenge.wenance.model.Currency;

public class CurrencyHelper {


    public static CryptoCurrency getCrytoCurrency(CurrencyTypeEnum currencyType, Currency currency){

        switch (currencyType){
            case BTCARS:
                return currency.getCryptoCurrencyGroup().getBtcars();
            case DAIARS:
                return currency.getCryptoCurrencyGroup().getDaiars();
            case BTCDAI:
                return currency.getCryptoCurrencyGroup().getBtcdai();
            case DAIUSD:
                return currency.getCryptoCurrencyGroup().getDaiusd();
            case ETHARS:
                return currency.getCryptoCurrencyGroup().getEthars();
            case ETHDAI:
                return currency.getCryptoCurrencyGroup().getEthdai();
            default:
                throw new IllegalStateException("Unexpected value: " + currencyType);
        }

    }

}
