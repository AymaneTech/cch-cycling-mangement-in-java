package com.wora.comptetition.domain.entity;

import com.wora.common.domain.valueObject.Timestamp;
import com.wora.common.infrastructure.persistence.DurationConverter;
import com.wora.comptetition.domain.valueObject.GeneralResultId;
import com.wora.rider.domain.entity.Rider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;

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

    @Column(name = "total_time")
    @Convert(converter = DurationConverter.class)
    private Duration totalTime;

    private Long position;

    @Embedded
    private Timestamp timestamp;

    public GeneralResult(Competition competition, Rider rider) {
        this.id = new GeneralResultId(rider.getId(), competition.getId());
        this.competition = competition;
        this.rider = rider;
    }
}
