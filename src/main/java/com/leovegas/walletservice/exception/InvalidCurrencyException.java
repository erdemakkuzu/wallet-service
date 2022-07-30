package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidCurrencyException extends RuntimeException {
    private String invalidCurrency;
}
