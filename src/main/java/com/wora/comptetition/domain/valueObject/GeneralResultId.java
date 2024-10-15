package com.wora.comptetition.domain.valueObject;

import com.wora.rider.domain.valueObject.RiderId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record GeneralResultId(
        @Column(name = "rider_id")
        RiderId riderId,

        @Column(name = "competition_id")
        CompetitionId id
) {
}
