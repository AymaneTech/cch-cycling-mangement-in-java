package com.wora.rider.application.mapper;

import com.wora.common.application.mapper.BaseMapper;
import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.domain.entity.Team;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface TeamMapper extends BaseMapper<Team, TeamRequestDto, TeamResponseDto> {
}
