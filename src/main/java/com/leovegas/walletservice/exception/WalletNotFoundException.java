package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletNotFoundException extends RuntimeException {
    private Long walletId;
}
