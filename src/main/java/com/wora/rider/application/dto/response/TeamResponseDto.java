package com.wora.rider.application.dto.response;

import com.wora.rider.application.dto.embeddable.EmbeddableRider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TeamResponseDto(@NotNull UUID id,
                              @NotBlank String name,
                              @NotBlank String country,
                              List<EmbeddableRider>riders
) {
}