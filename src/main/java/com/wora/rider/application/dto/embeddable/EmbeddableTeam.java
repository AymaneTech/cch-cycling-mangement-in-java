package com.wora.rider.application.dto.embeddable;

import com.wora.rider.domain.valueObject.TeamId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmbeddableTeam(@NotNull TeamId id,
                             @NotBlank String name,
                             @NotBlank String country) {
}
