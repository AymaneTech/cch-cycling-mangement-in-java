package com.wora.comptetition.domain.valueObject;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record CompetitionId(UUID value) {
}
