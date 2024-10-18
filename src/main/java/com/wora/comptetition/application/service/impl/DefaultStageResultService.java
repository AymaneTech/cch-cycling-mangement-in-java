package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.PassedStageRequestDto;
import com.wora.comptetition.application.dto.response.PassedStageResponseDto;
import com.wora.comptetition.application.service.StageResultService;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.comptetition.domain.repository.StageRepository;
import com.wora.comptetition.domain.repository.StageResultRepository;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.repository.RiderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class DefaultStageResultService implements StageResultService {
    private final StageResultRepository stageResultRepository;
    private final StageRepository stageRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper mapper;

    @Override
    public PassedStageResponseDto savePassedStage(PassedStageRequestDto dto) {
        final Rider rider = riderRepository.findById(dto.riderId())
                .orElseThrow(() -> new EntityNotFoundException(dto.riderId()));
        final Stage stage = stageRepository.findById(dto.stageId())
                .orElseThrow(() -> new EntityNotFoundException(dto.stageId()));

        final StageResult savedResult = stageResultRepository.save(new StageResult(rider, stage, dto.duration()));
        return mapper.map(savedResult, PassedStageResponseDto.class);
    }
}
