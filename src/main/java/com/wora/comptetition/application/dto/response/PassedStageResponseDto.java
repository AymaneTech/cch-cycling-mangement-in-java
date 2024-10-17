package com.wora.comptetition.application.dto.response;

import com.wora.comptetition.domain.valueObject.StageId;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

public record PassedStageResponseDto(@NotNull StageResponseDto stage,
                                     @NotNull RiderResponseDto rider,
                                     @NotNull @Positive Duration duration,
                                     @NotNull @Positive Integer position
) {
}
