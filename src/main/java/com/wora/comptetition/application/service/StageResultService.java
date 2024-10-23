package com.wora.comptetition.application.service;

import com.wora.comptetition.application.dto.request.StageResultRequestDto;
import com.wora.comptetition.application.dto.response.StageResultResponseDto;
import com.wora.comptetition.domain.valueObject.StageId;
import com.wora.rider.domain.valueObject.RiderId;

import java.util.List;

public interface StageResultService {
    List<StageResultResponseDto> findAll();

    StageResultResponseDto findByStageIdAndRiderId(StageId stageId, RiderId riderId);

    StageResultResponseDto savePassedStage(StageResultRequestDto dto);

    void delete(StageId stageId, RiderId riderId);


}
