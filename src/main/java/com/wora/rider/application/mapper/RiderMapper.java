package com.wora.rider.application.mapper;

import com.wora.common.application.mapper.BaseMapper;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.domain.entity.Rider;
import org.mapstruct.Mapper;


@Mapper(config = BaseMapper.class)
public interface RiderMapper extends BaseMapper<Rider, RiderRequestDto, RiderResponseDto> {
}
