package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CreateWalletResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("currency")
    private String currency;


}
