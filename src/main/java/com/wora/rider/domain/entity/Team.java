package com.wora.rider.domain.entity;

import com.wora.rider.domain.valueObject.TeamId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "teams")

@Getter
@Setter
@NoArgsConstructor
public class Team {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private TeamId id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Rider> riders;

    public Team(TeamId id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
