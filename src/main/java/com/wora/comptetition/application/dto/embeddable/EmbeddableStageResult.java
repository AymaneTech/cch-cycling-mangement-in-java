package com.wora.comptetition.application.dto.embeddable;

import com.wora.rider.application.dto.embeddable.EmbeddableRider;

import java.time.Duration;

public record EmbeddableStageResult(
        EmbeddableRider rider,
        Integer position,
        Duration duration
) {
}
