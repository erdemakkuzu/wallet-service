package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePlayerResponse{

    @JsonProperty("player_name")
    private String player_name;

    @JsonProperty("player_id")
    private Long player_id;
}