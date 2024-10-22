package com.wora.rider.application.dto.response;

import com.wora.rider.application.dto.embeddable.EmbeddableRider;
import com.wora.rider.domain.valueObject.TeamId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TeamResponseDto(@NotNull TeamId id,
                              @NotBlank String name,
                              @NotBlank String country,
                              List<EmbeddableRider>riders
) {
}