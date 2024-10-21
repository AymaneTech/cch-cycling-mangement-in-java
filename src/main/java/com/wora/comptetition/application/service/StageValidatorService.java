package com.wora.comptetition.application.service;

import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.domain.entity.Stage;

import java.util.List;

public interface StageValidatorService {
    List<Stage> validateAndGetStages(List<StageRequestDto> dtos);
}
