package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PerformTransactionResponse {

    @JsonProperty("walletId")
    private Long walletId;

    @JsonProperty("hash_id")
    private String hashId;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("current_balance")
    private Double currentBalance;
}
