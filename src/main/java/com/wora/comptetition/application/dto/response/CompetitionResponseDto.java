package com.wora.comptetition.application.dto.response;

import com.wora.comptetition.application.dto.embeddable.EmbeddableGeneralResultDto;
import com.wora.comptetition.application.dto.embeddable.EmbeddableStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CompetitionResponseDto(@NotNull UUID id,
                                     @NotBlank String name,
                                     @NotNull LocalDate startDate,
                                     @NotNull LocalDate endDate,
                                     @NotNull List<EmbeddableStage> stages,
                                     @NotNull List<EmbeddableGeneralResultDto> generalResults
                                     ) {
}
