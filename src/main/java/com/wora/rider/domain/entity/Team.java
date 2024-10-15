package com.wora.rider.domain.entity;

import com.wora.rider.domain.valueObject.TeamId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private TeamId id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String country;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Rider> riders;
}
