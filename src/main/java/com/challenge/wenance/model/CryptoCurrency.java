package com.challenge.wenance.model;


import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CryptoCurrency {

    protected String currency;
    protected String bid_currency;
    protected String ask_currency;
    protected BigDecimal purchase_price;
    protected BigDecimal selling_price;
    protected String market_identifier;


}
