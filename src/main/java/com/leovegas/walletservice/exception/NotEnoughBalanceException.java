package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NotEnoughBalanceException extends RuntimeException{
    private String walletBalance;
}
