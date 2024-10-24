package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.StageResultRequestDto;
import com.wora.comptetition.application.dto.response.StageResultResponseDto;
import com.wora.comptetition.application.mapper.StageResultMapper;
import com.wora.comptetition.application.service.StageResultService;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.comptetition.domain.exception.RiderNotSubscribeCompetitionException;
import com.wora.comptetition.domain.repository.StageRepository;
import com.wora.comptetition.domain.repository.StageResultRepository;
import com.wora.comptetition.domain.valueObject.StageId;
import com.wora.comptetition.domain.valueObject.StageResultId;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.repository.RiderRepository;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class DefaultStageResultService implements StageResultService {
    private final StageResultRepository stageResultRepository;
    private final RiderRepository riderRepository;
    private final StageRepository stageRepository;
    private final StageResultMapper mapper;

    @Override
    public List<StageResultResponseDto> findAll() {
        return stageResultRepository.findAll()
                .stream().map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public StageResultResponseDto findByStageIdAndRiderId(StageId stageId, RiderId riderId) {
        StageResultId stageResultId = new StageResultId(stageId, riderId);
        return stageResultRepository.findById(stageResultId)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("stage result", stageResultId));
    }

    @Override
    public StageResultResponseDto savePassedStage(StageResultRequestDto dto) {
        final Rider rider = riderRepository.findById(new RiderId(dto.riderId()))
                .orElseThrow(() -> new EntityNotFoundException(dto.riderId()));
        final Stage stage = stageRepository.findById(new StageId(dto.stageId()))
                .orElseThrow(() -> new EntityNotFoundException(dto.stageId()));

        if (!isRiderJoinedCompetition(rider, stage))
            throw new RiderNotSubscribeCompetitionException("This rider not subscribe this competition");

        final StageResult savedResult = stageResultRepository.save(new StageResult(rider, stage, dto.duration()));
        return mapper.toResponseDto(savedResult);
    }

    @Override
    public void delete(StageId stageId, RiderId riderId) {
        StageResultId stageResultId = new StageResultId(stageId, riderId);
        if (!stageResultRepository.existsById(stageResultId))
            throw new EntityNotFoundException(stageResultId);

        stageResultRepository.deleteById(stageResultId);
    }

    private boolean isRiderJoinedCompetition(Rider rider, Stage stage) {
        return rider.getGeneralResults().stream()
                .map(GeneralResult::getCompetition)
                .anyMatch(competition -> stage.getCompetition().getId().equals(competition.getId()));
    }

}
