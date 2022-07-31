package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePlayerResponse {

    @JsonProperty("player_name")
    private String playerName;

    @JsonProperty("player_id")
    private Long playerId;
}
