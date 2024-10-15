package com.wora.comptetition.domain.valueObject;

import com.wora.rider.domain.valueObject.RiderId;
import jakarta.persistence.Column;

public record StageResultId(
        @Column(name = "stage_id") StageId stageId,
        @Column(name = "rider_id") RiderId riderId
) {
}
