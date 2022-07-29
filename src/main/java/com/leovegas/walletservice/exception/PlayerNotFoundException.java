package com.leovegas.walletservice.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerNotFoundException extends RuntimeException{
    private String playerName;

}