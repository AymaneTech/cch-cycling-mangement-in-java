package com.wora.comptetition.application.dto.request;

import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.constraints.NotNull;

public record SubscribeToCompetitionRequestDto(@NotNull CompetitionId competitionId, @NotNull RiderId riderId) {
}
