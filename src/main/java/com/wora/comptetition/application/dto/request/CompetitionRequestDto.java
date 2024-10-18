package com.wora.comptetition.application.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CompetitionRequestDto(@NotBlank String name,
                                    @NotNull @Future LocalDate startDate,
                                    @NotNull @Future LocalDate endDate
) {
}
