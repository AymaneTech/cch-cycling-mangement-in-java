package com.wora.comptetition.application.dto.embeddable;

import com.wora.comptetition.domain.valueObject.StageId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EmbeddableStage(@NotNull StageId id,
                              @NotNull @Positive Integer stageNumber,
                              @NotNull @Positive Double distance,
                              @NotBlank String startLocation,
                              @NotBlank String endLocation,
                              @NotNull LocalDate date
) {
}