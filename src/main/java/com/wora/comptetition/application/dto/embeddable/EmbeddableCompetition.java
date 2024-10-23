package com.wora.comptetition.application.dto.embeddable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record EmbeddableCompetition(@NotNull UUID id,
                                    @NotBlank String name,
                                    @NotNull LocalDate startDate,
                                    @NotNull LocalDate endDate
) {
}
