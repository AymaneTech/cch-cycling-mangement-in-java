package com.wora.comptetition.application.dto.response;

import com.wora.comptetition.domain.valueObject.CompetitionId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmbeddableCompetition(@NotNull CompetitionId id,
                                    @NotBlank String name,
                                    @NotNull LocalDate startDate,
                                    @NotNull LocalDate endDate
) {
}
