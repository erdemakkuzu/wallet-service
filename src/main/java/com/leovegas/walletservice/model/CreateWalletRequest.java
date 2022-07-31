package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("currency")
    private String currency;
}
