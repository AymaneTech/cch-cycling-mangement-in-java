package com.wora.comptetition.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GeneralResultRequestDto(@NotNull UUID competitionId, @NotNull UUID riderId) {
}
