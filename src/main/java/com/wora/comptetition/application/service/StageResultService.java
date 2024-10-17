package com.wora.comptetition.application.service;

import com.wora.comptetition.application.dto.request.PassedStageRequestDto;
import com.wora.comptetition.application.dto.response.PassedStageResponseDto;

public interface StageResultService {
    PassedStageResponseDto savePassedStage(PassedStageRequestDto dto);
}
