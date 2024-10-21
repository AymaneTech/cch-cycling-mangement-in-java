package com.wora.comptetition.application.service;

import com.wora.comptetition.application.dto.request.SubscribeToCompetitionRequestDto;
import com.wora.comptetition.application.dto.response.SubscribeToCompetitionResponseDto;

public interface GeneralResultService {
    SubscribeToCompetitionResponseDto subscribeToCompetition(SubscribeToCompetitionRequestDto dto);

}
