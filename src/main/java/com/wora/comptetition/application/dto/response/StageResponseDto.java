package com.wora.comptetition.application.dto.response;

import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.StageId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record StageResponseDto(
        @NotNull
        StageId id,

        @NotNull
        @Positive
        Integer stageNumber,

        @NotNull
        @Positive
        Double distance,

        @NotBlank
        String startLocation,

        @NotBlank
        String endLocation,

        @NotNull
        LocalDate date,

        @NotNull
        CompetitionResponseDto competition
) {
}
