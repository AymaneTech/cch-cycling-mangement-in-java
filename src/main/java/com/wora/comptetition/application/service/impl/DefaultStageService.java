package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.application.service.StageService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.repository.StageRepository;
import com.wora.comptetition.domain.valueObject.StageId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class DefaultStageService implements StageService {
    private final StageRepository repository;
    private final CompetitionRepository competitionRepository;
    private final ModelMapper mapper;

    @Override
    public List<StageResponseDto> findAll() {
        return repository.findAll()
                .stream().map(this::toResponseDto)
                .toList();
    }

    @Override
    public StageResponseDto findById(StageId id) {
        return repository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public StageResponseDto create(StageRequestDto dto) {
        final Competition competition = competitionRepository.findById(dto.competitionId())
                .orElseThrow(() -> new EntityNotFoundException(dto.competitionId()));

        final Stage mappedStage = mapper.map(dto, Stage.class)
                .setCompetition(competition);

        Stage savedStage = repository.save(mappedStage);
        return mapper.map(savedStage, StageResponseDto.class);
    }

    @Override
    public StageResponseDto update(StageId id, StageRequestDto dto) {
        final Stage stage = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        final Competition competition = competitionRepository.findById(dto.competitionId())
                .orElseThrow(() -> new EntityNotFoundException(id));

        mapper.map(dto, stage);
        stage.setCompetition(competition);
        return toResponseDto(stage);
    }

    @Override
    public void delete(StageId id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(id);

        repository.deleteById( id);
    }

    private StageResponseDto toResponseDto(Stage stage) {
        return mapper.map(stage, StageResponseDto.class);
    }
}
