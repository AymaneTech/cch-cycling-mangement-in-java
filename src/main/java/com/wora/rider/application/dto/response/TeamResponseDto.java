package com.wora.rider.application.dto.response;

import com.wora.rider.domain.valueObject.TeamId;

import java.util.List;

public record TeamResponseDto(
        TeamId id,
        String name,
        String country,
        List<RiderResponseDto> riders
) {
}
