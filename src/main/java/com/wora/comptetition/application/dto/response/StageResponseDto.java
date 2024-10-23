package com.wora.comptetition.application.dto.response;

import com.wora.comptetition.application.dto.embeddable.EmbeddableCompetition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public record StageResponseDto(@NotNull UUID id,
                               @NotNull @Positive Integer stageNumber,
                               @NotNull @Positive Double distance,
                               @NotBlank String startLocation,
                               @NotBlank String endLocation,
                               @NotNull LocalDate date,
                               @NotNull EmbeddableCompetition competition
) {
}
