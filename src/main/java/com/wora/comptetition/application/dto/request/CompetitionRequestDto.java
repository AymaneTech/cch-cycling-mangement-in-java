package com.wora.comptetition.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CompetitionRequestDto(@NotBlank String name,
                                    @NotNull LocalDate startDate,
                                    @NotNull LocalDate endDate
) {
}
