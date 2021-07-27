package com.challenge.wenance.model;

import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class Daiars extends CryptoCurrency {

    @Builder
    public Daiars(String currency, String bid_currency, String ask_currency, BigDecimal purchase_price, BigDecimal selling_price, String market_identifier) {
        super(currency, bid_currency, ask_currency, purchase_price, selling_price, market_identifier);
    }
}
