package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PerformTransactionResponse {

    @JsonProperty("walletId")
    private Long walletId;

    @JsonProperty("transaction_hash_id")
    private String transactionHashId;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("current_balance")
    private Double currentBalance;
}
