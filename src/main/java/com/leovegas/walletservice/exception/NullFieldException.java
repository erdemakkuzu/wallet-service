package com.leovegas.walletservice.exception;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class NullFieldException extends RuntimeException{
    private String fieldName;
}
