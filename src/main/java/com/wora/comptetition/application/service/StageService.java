package com.wora.comptetition.application.service;

import com.wora.common.application.service.CrudService;
import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.valueObject.StageId;

public interface StageService extends CrudService<Stage, StageId, StageRequestDto, StageResponseDto> {
}
