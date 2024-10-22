package com.wora.comptetition.application.mapper;

import com.wora.common.application.mapper.BaseMapper;
import com.wora.comptetition.application.dto.request.PassedStageRequestDto;
import com.wora.comptetition.application.dto.response.PassedStageResponseDto;
import com.wora.comptetition.domain.entity.StageResult;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface StageResultMapper extends BaseMapper<StageResult, PassedStageRequestDto, PassedStageResponseDto> {
}
