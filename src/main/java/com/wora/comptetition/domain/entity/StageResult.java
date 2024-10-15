package com.wora.comptetition.domain.entity;

import com.wora.comptetition.domain.valueObject.StageResultId;
import com.wora.rider.domain.entity.Rider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Entity
@Table(name = "stage_results")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StageResult {

    @EmbeddedId
    private StageResultId id;

    private Integer position;

    private Duration duration;

    @MapsId("rider_id")
    @ManyToOne
    private Rider rider;

    @MapsId("stage_id")
    @ManyToOne
    private Stage stage;
}
