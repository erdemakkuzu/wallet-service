package com.leovegas.walletservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreatePlayerRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("gender")
    private String gender;
}
