package com.wora.rider.application.service;

import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.domain.valueObject.RiderId;

import java.util.List;

public interface RiderService {
    List<RiderResponseDto> findAll();

    RiderResponseDto findById(RiderId id);

    RiderResponseDto create(RiderRequestDto dto);

    RiderResponseDto update(RiderId id, RiderRequestDto dto);

    void delete(RiderId id);
}
