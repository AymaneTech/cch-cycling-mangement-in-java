package com.wora.comptetition.application.service;

import com.wora.common.application.service.CrudService;
import com.wora.comptetition.application.dto.request.CompetitionRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.valueObject.CompetitionId;

public interface CompetitionService extends CrudService<Competition, CompetitionId, CompetitionRequestDto, CompetitionResponseDto> {
}
