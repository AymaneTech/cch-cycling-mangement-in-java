package com.wora.comptetition.domain.valueObject;

import com.wora.rider.domain.valueObject.RiderId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;

public record StageResultId(
        @AttributeOverride(name = "value", column = @Column(name = "stageid"))
        StageId stageId,

        @AttributeOverride(name = "value", column = @Column(name = "riderid"))
        RiderId riderId
) {
}
