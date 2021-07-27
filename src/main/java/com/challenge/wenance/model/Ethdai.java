package com.challenge.wenance.model;

import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ethdai extends CryptoCurrency {

    @Builder
    public Ethdai(String currency, String bid_currency, String ask_currency, BigDecimal purchase_price, BigDecimal selling_price, String market_identifier) {
        super(currency, bid_currency, ask_currency, purchase_price, selling_price, market_identifier);
    }
}
