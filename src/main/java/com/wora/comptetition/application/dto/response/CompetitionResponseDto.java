package com.wora.comptetition.application.dto.response;

import com.wora.comptetition.application.dto.embeddable.EmbeddableStage;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CompetitionResponseDto(@NotNull CompetitionId id,
                                     @NotBlank String name,
                                     @NotNull LocalDate startDate,
                                     @NotNull LocalDate endDate,
                                     @NotNull List<EmbeddableStage> stages
                                     ) {
}
