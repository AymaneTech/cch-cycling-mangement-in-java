package com.wora.comptetition.domain.entity;

import com.wora.common.domain.valueObject.Timestamp;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "competitions")

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Competition {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private CompetitionId id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Size(min = 4, max = 4)
    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Stage> stages;

    @OneToMany(mappedBy = "competition")
    private List<GeneralResult> generalResults;

    @Embedded
    private Timestamp timestamp;
}
