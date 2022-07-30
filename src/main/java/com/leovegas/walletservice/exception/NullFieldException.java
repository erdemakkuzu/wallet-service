package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class NullFieldException extends RuntimeException {
    private String fieldName;
}
