package com.wora.rider.domain.entity;

import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "riders")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rider {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private RiderId id;

    @Embedded
    private Name name;

    @NotBlank
    private String nationality;

    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Team team;

    @OneToMany(mappedBy = "rider")
    private List<StageResult> stageResults;

    @OneToMany(mappedBy = "rider")
    private List<GeneralResult> generalResults;
}
