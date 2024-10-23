package com.wora.comptetition.application.dto.request;

import com.wora.comptetition.domain.valueObject.StageId;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record PassedStageRequestDto(@NotNull StageId stageId,
                                    @NotNull RiderId riderId,
                                    @NotNull Duration duration
) {
}
