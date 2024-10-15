package com.wora.comptetition.domain.entity;

import com.wora.comptetition.domain.valueObject.StageId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletionException;

@Entity
@Table(name = "stages")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stage {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private StageId id;

    @NotNull
    @Positive
    private Integer stageNumber;

    @NotNull
    @Positive
    private Double distance;

    private String startLocation;

    private String endLocation;

    private LocalDate date;

    @ManyToOne
    private Competition competition;

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StageResult> stageResults;

}
