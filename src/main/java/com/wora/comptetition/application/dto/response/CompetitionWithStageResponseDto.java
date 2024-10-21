package com.wora.comptetition.application.dto.response;

import java.util.List;

public record CompetitionWithStageResponseDto(
        CompetitionResponseDto competition,
        List<StageResponseDto> stages
        ) {
}
