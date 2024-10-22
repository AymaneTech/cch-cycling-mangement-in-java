package com.wora.comptetition.application.mapper;

import com.wora.common.application.mapper.BaseMapper;
import com.wora.comptetition.application.dto.request.CompetitionRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.domain.entity.Competition;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface CompetitionMapper extends BaseMapper<Competition, CompetitionRequestDto, CompetitionResponseDto> {
}
