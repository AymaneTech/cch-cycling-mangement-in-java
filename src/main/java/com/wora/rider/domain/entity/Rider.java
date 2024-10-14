package com.wora.rider.domain.entity;

import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private String nationality;

    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private Team team;
}
