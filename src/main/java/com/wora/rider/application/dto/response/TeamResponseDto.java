package com.wora.rider.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wora.rider.domain.valueObject.TeamId;

import java.util.List;

public record TeamResponseDto(
        @JsonProperty("id")
        TeamId id,
        @JsonProperty("name")
        String name,
        @JsonProperty("country")
        String country,
        @JsonProperty
        List<RiderResponseDto> riders
) {
}
