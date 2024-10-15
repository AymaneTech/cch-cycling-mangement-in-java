package com.wora.comptetition.domain.entity;

import com.wora.comptetition.domain.valueObject.StageId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private Integer stageNumber;

    private Double distance;

    private String startLocation;

    private String endLocation;

    private LocalDate date;

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StageResult> stageResults;
}
