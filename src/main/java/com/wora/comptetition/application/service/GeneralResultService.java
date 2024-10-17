package com.wora.comptetition.application.service;

import com.wora.comptetition.application.dto.request.SubscribeToCompetitionRequestDto;
import com.wora.comptetition.application.dto.response.SubscribeToCompetitionResponseDto;
import com.wora.comptetition.domain.entity.GeneralResult;

public interface GeneralResultService {
    SubscribeToCompetitionResponseDto subscribeToCompetition(SubscribeToCompetitionRequestDto dto);

}
