package com.wora.comptetition.domain.entity;

import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.GeneralResultId;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.valueObject.RiderId;
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

    public GeneralResult(Competition competition, Rider rider) {
        this.competition = competition;
        this.rider = rider;
        this.id = new GeneralResultId(rider.getId(), competition.getId());
    }
}
