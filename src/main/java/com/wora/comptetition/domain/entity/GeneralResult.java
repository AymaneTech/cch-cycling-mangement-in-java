package com.wora.comptetition.domain.entity;

import com.wora.common.domain.valueObject.Timestamp;
import com.wora.comptetition.domain.valueObject.GeneralResultId;
import com.wora.rider.domain.entity.Rider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "general_results")

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class GeneralResult {

    @EmbeddedId
    private GeneralResultId id;

    @MapsId("competitionId")
    @ManyToOne
    private Competition competition;

    @MapsId("riderId")
    @ManyToOne
    private Rider rider;

    @Embedded
    private Timestamp timestamp;

    public GeneralResult(Competition competition, Rider rider) {
        this.id = new GeneralResultId(rider.getId(), competition.getId());
        this.competition = competition;
        this.rider = rider;
    }
}
