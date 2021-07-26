package com.challenge.wenance.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CryptoCurrencyGroup {

    private Daiars daiars;
    private Btcars btcars;
    private Daiusd daiusd;
    private Btcdai btcdai;
    private Ethars ethars;
    private Ethdai ethdai;

    @Transient
    private List errors;

}
