package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetWalletResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("create_date")
    private Date createDate;

}