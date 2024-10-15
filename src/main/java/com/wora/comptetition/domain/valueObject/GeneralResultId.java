package com.wora.comptetition.domain.valueObject;

import com.wora.rider.domain.valueObject.RiderId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record GeneralResultId(
        @AttributeOverride(name = "value", column = @Column(name = "riderid"))
        RiderId riderId,

        @AttributeOverride(name = "value", column = @Column(name = "competitionid"))
        CompetitionId competitionId
) {
}
