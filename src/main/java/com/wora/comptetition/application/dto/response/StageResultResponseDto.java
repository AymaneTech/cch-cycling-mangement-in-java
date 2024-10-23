package com.wora.comptetition.application.dto.response;

import com.wora.rider.application.dto.response.RiderResponseDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

public record StageResultResponseDto(@NotNull StageResponseDto stage,
                                     @NotNull RiderResponseDto rider,
                                     @NotNull @Positive Duration duration,
                                     @NotNull @Positive Integer position
) {
}
