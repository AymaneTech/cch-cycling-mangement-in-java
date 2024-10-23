package com.wora.comptetition.application.mapper;

import com.wora.common.application.mapper.BaseMapper;
import com.wora.comptetition.application.dto.request.StageResultRequestDto;
import com.wora.comptetition.application.dto.response.StageResultResponseDto;
import com.wora.comptetition.domain.entity.StageResult;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface StageResultMapper extends BaseMapper<StageResult, StageResultRequestDto, StageResultResponseDto> {
}
