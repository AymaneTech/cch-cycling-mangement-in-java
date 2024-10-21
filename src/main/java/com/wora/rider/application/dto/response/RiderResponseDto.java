package com.wora.rider.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;

import java.time.LocalDate;

public record RiderResponseDto(
        @JsonProperty("id")
        RiderId id,
        @JsonProperty("name")
        Name name,
        @JsonProperty("nationality")
        String nationality,
        @JsonProperty("dateOfBirth")
        LocalDate dateOfBirth,
        @JsonProperty("team")
        TeamResponseDto team
) {
}
