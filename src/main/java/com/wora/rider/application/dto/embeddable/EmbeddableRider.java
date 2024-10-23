package com.wora.rider.application.dto.embeddable;

import com.wora.rider.domain.valueObject.Name;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record EmbeddableRider(@NotNull UUID id,
                              @NotBlank Name name,
                              @NotBlank String nationality,
                              @NotNull @Past LocalDate dateOfBirth
) {
}
