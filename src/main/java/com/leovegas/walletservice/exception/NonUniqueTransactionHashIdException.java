package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NonUniqueTransactionHashIdException extends RuntimeException {
    private String transactionHashId;
}
