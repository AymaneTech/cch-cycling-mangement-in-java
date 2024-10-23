package com.wora.comptetition.application.service;

import com.wora.comptetition.application.dto.request.GeneralResultRequestDto;
import com.wora.comptetition.application.dto.response.GeneralResultResponseDto;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.rider.domain.valueObject.RiderId;

import java.util.List;

public interface GeneralResultService {
    List<GeneralResultResponseDto> findAll();

    GeneralResultResponseDto findByCompetitionIdAndRiderId(CompetitionId competitionId, RiderId riderId);

    GeneralResultResponseDto subscribeToCompetition(GeneralResultRequestDto dto);

    void delete(CompetitionId competitionId, RiderId riderId);
}
