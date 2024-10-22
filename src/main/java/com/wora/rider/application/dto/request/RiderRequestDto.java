package com.wora.rider.application.dto.request;

import com.wora.rider.domain.valueObject.Name;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RiderRequestDto(@NotNull Name name,
                              @NotBlank String nationality,
                              @NotNull LocalDate dateOfBirth,
                              @NotNull UUID teamId
) {
}
