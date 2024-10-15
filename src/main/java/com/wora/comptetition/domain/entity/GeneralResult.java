package com.wora.comptetition.domain.entity;

import com.wora.comptetition.domain.valueObject.GeneralResultId;
import com.wora.rider.domain.entity.Rider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "general_results")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResult {

    @EmbeddedId
    private GeneralResultId id;

    @ManyToOne
    private Competition competition;

    @ManyToOne
    private Rider rider;
}
