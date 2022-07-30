package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Balance {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("balance")
    private Double balance;

}
