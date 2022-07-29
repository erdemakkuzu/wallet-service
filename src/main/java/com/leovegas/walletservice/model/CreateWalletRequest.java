package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
public class CreateWalletRequest {

    @JsonProperty("name")
    @JsonPropertyDescription("Name of the wallet")
    private String name;

    @JsonProperty("balance")
    @JsonPropertyDescription("Initial balance of the wallet")
    private Double balance;

    @JsonProperty("currency")
    @JsonPropertyDescription("Currency of wallet")
    private String currency;
}
