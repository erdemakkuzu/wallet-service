package com.leovegas.walletservice.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerNotFoundException extends RuntimeException {
    private String playerName;

}
