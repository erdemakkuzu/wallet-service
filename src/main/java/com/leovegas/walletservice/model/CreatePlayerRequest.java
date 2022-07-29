package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class CreatePlayerRequest {

    @JsonProperty("player_name")
    @JsonPropertyDescription("Player name of the account")
    private String name;

    @JsonPropertyDescription("First name of the account owner")
    @JsonProperty("first_name")
    private String firstName;

    @JsonPropertyDescription("Last name of the account owner")
    @JsonProperty("last_name")
    private String lastName;

    @JsonPropertyDescription("Age of the account owner")
    @JsonProperty("age")
    private Integer age;

    @JsonPropertyDescription("Gender of the account owner")
    @JsonProperty("gender")
    private String gender;
}
