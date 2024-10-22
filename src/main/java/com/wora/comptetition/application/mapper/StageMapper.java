package com.wora.comptetition.application.mapper;

import com.wora.common.application.mapper.BaseMapper;
import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.domain.entity.Stage;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface StageMapper extends BaseMapper<Stage, StageRequestDto, StageResponseDto> {
}
