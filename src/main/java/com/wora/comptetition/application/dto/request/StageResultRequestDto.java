package com.wora.comptetition.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.util.UUID;

public record StageResultRequestDto(@NotNull UUID stageId,
                                    @NotNull UUID riderId,
                                    @NotNull Duration duration
) {
}
