package com.wora.rider.domain.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record Name(
        @NotBlank
        @Column(name = "first_name")
        String firstName,

        @NotBlank
        @Column(name = "last_name")
        String lastName
) {
}
