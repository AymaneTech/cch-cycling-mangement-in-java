package com.wora.comptetition.domain.entity;

import com.wora.comptetition.domain.valueObject.StageResultId;
import com.wora.rider.domain.entity.Rider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Entity
@Table(name = "stage_results")

@Getter
@Setter
@NoArgsConstructor
public class StageResult {

    @EmbeddedId
    private StageResultId id;

    @NotNull
    @Positive
    private Integer position;

    @NotNull
    private Duration duration;

    @MapsId("riderId")
    @ManyToOne
    private Rider rider;

    @MapsId("stageId")
    @ManyToOne
    private Stage stage;

    public StageResult(Rider rider, Stage stage, Duration duration) {
        this.id = new StageResultId(stage.getId(), rider.getId());
        this.rider = rider;
        this.stage = stage;
        this.duration = duration;
    }
}
