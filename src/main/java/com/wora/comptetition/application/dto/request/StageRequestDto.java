package com.wora.comptetition.application.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public record StageRequestDto(@NotNull @Positive Integer stageNumber,
                              @NotNull @Positive Double distance,
                              @NotBlank String startLocation,
                              @NotBlank String endLocation,
                              @NotNull @Future LocalDate date,
                              @NotNull UUID competitionId
) {
}
