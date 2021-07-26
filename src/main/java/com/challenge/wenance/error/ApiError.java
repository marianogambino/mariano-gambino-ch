package com.challenge.wenance.error;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@SwaggerDefinition
public class ApiError {

    private HttpStatus code;
    private String message;
}
