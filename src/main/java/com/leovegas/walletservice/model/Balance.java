package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Balance {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("balance")
    private Double balance;

}
