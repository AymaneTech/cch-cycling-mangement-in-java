package com.wora.rider.domain.valueObject;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record Name(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
