package com.wora.common.application.service;

import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.domain.valueObject.RiderId;

import java.util.List;

public interface CrudService<Entity, Id, RequestDto, ResponseDto> {
    List<ResponseDto> findAll();

    ResponseDto findById(Id id);

    ResponseDto create(RequestDto dto);

    ResponseDto update(Id id, RequestDto dto);

    void delete(Id id);
}
