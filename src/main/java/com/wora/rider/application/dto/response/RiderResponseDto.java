package com.wora.rider.application.dto.response;

import com.wora.common.application.validation.Adult;
import com.wora.rider.application.dto.embeddable.EmbeddableTeam;
import com.wora.rider.domain.valueObject.Name;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RiderResponseDto(@NotNull UUID id,
                               @NotBlank Name name,
                               @NotBlank String nationality,
                               @NotNull @Adult LocalDate dateOfBirth,
                               @NotNull EmbeddableTeam team
) {
}
