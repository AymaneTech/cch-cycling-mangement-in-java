package com.wora.rider.application.service;

import com.wora.common.application.service.CrudService;
import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.valueObject.TeamId;

public interface TeamService extends CrudService<Team, TeamId, TeamRequestDto, TeamResponseDto> {
}
