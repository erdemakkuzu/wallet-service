package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyMisMatchException extends RuntimeException{
    private String mismatchCurrency;
}
