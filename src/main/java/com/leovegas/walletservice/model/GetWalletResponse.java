package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class GetWalletResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("create_date")
    private Date createDate;

    @JsonProperty("currency")
    private String currency;
}
