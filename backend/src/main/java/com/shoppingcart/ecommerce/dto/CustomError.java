package com.shoppingcart.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

}
