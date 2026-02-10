package com.bajaj.bfhl.bajajqualifier1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    @JsonProperty("is_success")
    private boolean success;

    @JsonProperty("official_email")
    private String officialEmail;

    private Object data;
}
