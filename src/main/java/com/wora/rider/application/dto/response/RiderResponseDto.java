package com.wora.rider.application.dto.response;

import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;

import java.time.LocalDate;

public record RiderResponseDto(
        RiderId id,
        Name name,
        String nationality,
        LocalDate dateOfBirth,
        TeamResponseDto team
) {
}
