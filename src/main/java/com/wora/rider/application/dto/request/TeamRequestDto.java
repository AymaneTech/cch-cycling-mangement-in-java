package com.wora.rider.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TeamRequestDto(
        @NotBlank String name,

        @NotBlank String country) {
}
