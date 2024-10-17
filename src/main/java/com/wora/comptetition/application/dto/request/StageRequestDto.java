package com.wora.comptetition.application.dto.request;

import com.wora.comptetition.domain.valueObject.CompetitionId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record StageRequestDto(@NotNull @Positive Integer stageNumber,
                              @NotNull @Positive Double distance,
                              @NotBlank String startLocation,
                              @NotBlank String endLocation,
                              @NotNull LocalDate date,
                              @NotNull CompetitionId competitionId
) {
}
