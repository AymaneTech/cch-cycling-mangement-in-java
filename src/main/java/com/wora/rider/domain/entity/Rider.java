package com.wora.rider.domain.entity;

import com.wora.common.application.validation.Adult;
import com.wora.common.domain.valueObject.Timestamp;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "riders")

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class Rider {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private RiderId id;

    @Embedded
    private Name name;

    @NotBlank
    private String nationality;

    @NotNull
    @Adult
    private LocalDate dateOfBirth;

    @ManyToOne(optional = false)
    private Team team;

    @OneToMany(mappedBy = "rider")
    private List<StageResult> stageResults;

    @OneToMany(mappedBy = "rider")
    private List<GeneralResult> generalResults;

    @Embedded
    private Timestamp timestamp;

    public Rider(RiderId id, Name name, String nationality, LocalDate dateOfBirth, Team team) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.team = team;
    }

    public Rider(Name name, String nationality, LocalDate dateOfBirth, Team team) {
        this.name = name;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.team = team;
    }
}