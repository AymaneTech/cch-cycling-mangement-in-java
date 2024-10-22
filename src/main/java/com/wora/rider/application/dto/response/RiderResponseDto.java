package com.wora.rider.application.dto.response;

import com.wora.rider.application.dto.embeddable.EmbeddableTeam;
import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RiderResponseDto(@NotNull RiderId id,
                               @NotBlank Name name,
                               @NotBlank String nationality,
                               @NotNull @Past LocalDate dateOfBirth,
                               @NotNull EmbeddableTeam team
) {
}
