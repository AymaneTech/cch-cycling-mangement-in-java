package com.wora.comptetition.application.dto.response;

import com.wora.comptetition.domain.valueObject.StageId;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

public record PassedStageResponseDto(@NotNull StageId stageId,
                                     @NotNull RiderId riderId,
                                     @NotNull @Positive Duration duration
) {
}
