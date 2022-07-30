package com.leovegas.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NullPlayerNameException extends RuntimeException {
    private String playerName;
}
