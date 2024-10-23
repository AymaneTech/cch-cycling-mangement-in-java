package com.wora.rider.application.dto.response;

import com.wora.rider.application.dto.embeddable.EmbeddableTeam;
import com.wora.rider.domain.valueObject.Name;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record RiderResponseDto(@NotNull UUID id,
                               @NotBlank Name name,
                               @NotBlank String nationality,
                               @NotNull @Past LocalDate dateOfBirth,
                               @NotNull EmbeddableTeam team
) {
}
