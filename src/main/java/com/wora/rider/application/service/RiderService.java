package com.wora.rider.application.service;

import com.wora.common.application.service.CrudService;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.valueObject.RiderId;

public interface RiderService extends CrudService<Rider, RiderId, RiderRequestDto, RiderResponseDto> {
}
