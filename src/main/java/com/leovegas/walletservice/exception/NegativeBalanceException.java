package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NegativeBalanceException extends RuntimeException{
    private Double balance;
}
