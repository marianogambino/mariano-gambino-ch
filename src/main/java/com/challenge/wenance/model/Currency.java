package com.challenge.wenance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "currency")
public class Currency {

    @Transient
    public static final String SEQUENCE_NAME = "currency_sequence";

    @JsonIgnore
    @Id
    private Long id;

    @JsonProperty("object")
    private CryptoCurrencyGroup cryptoCurrencyGroup;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-3")
    private Date creationDate;
}
