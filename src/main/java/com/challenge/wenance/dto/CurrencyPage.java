package com.challenge.wenance.dto;

import com.challenge.wenance.model.Currency;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPage {
    private long totalItems;
    private int currentPage;
    private int totalPages;
    private List<Currency> currencies;

}
