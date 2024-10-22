package com.wora.rider.application.dto.embeddable;

import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record EmbeddableRider(@NotNull RiderId id,
                              @NotBlank Name name,
                              @NotBlank String nationality,
                              @NotNull @Past LocalDate dateOfBirth
) {
}
