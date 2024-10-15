package com.wora.rider.domain.exception;

import com.wora.rider.domain.valueObject.TeamId;
import lombok.Getter;

public class TeamNotFoundException extends RuntimeException {
    @Getter
    private final TeamId id;

    public TeamNotFoundException(TeamId id) {
        super("------------------  Team with id " + id.value() + " not found");
        this.id = id;
    }
}
