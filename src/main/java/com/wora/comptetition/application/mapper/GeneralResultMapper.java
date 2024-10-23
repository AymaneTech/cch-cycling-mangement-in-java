package com.wora.comptetition.application.mapper;

import com.wora.common.application.mapper.BaseMapper;
import com.wora.comptetition.application.dto.request.GeneralResultRequestDto;
import com.wora.comptetition.application.dto.response.GeneralResultResponseDto;
import com.wora.comptetition.domain.entity.GeneralResult;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface GeneralResultMapper extends BaseMapper<GeneralResult, GeneralResultRequestDto, GeneralResultResponseDto> {
}
