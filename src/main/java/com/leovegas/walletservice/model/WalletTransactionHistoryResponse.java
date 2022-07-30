package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WalletTransactionHistoryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("transactions")
    private List<TransactionResponse> transactionResponseList;

}
