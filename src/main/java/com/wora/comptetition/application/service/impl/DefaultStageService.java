package com.wora.comptetition.application.service.impl;

import com.wora.common.application.validation.validator.DateValidator;
import com.wora.common.domain.exception.EntityCreationException;
import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.application.mapper.StageMapper;
import com.wora.comptetition.application.service.StageService;
import com.wora.comptetition.application.service.StageValidatorService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.exception.CompetitionClosedException;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.repository.StageRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.StageId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final StageValidatorService stageValidatorService;
    private final StageMapper mapper;

    @Override
    public List<StageResponseDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public List<StageResponseDto> findAllByCompetitionId(CompetitionId competitionId) {
        return repository.findAllByCompetitionId(competitionId)
                .stream().map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public StageResponseDto findById(StageId id) {
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public StageResponseDto create(StageRequestDto dto) {
        final Competition competition = competitionRepository.findById(new CompetitionId(dto.competitionId()))
                .orElseThrow(() -> new EntityNotFoundException("competition", dto.competitionId()));

        if(competition.isClosed())
            throw new CompetitionClosedException("The competition you are trying to subscribe to is already closed");

        Stage mappedStage = mapToEntity(dto, competition);
        competition.getStages().add(mappedStage);
        validateStagesBetweenCompetitionStartAndEnd(competition.getStages(), competition);
        stageValidatorService.validateAndGetStages(competition.getStages());
        Stage savedStage = repository.save(mappedStage);
        return mapper.toResponseDto(savedStage);
    }

    @Override
    public StageResponseDto update(StageId id, StageRequestDto dto) {
        final Stage stage = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("stage", id));
        final Competition competition = competitionRepository.findById(new CompetitionId(dto.competitionId()))
                .orElseThrow(() -> new EntityNotFoundException("competition", id));

        stage.setStageNumber(dto.stageNumber())
                .setDistance(dto.distance())
                .setStartLocation(dto.startLocation())
                .setEndLocation(dto.endLocation())
                .setDate(dto.date())
                .setCompetition(competition);
        return mapper.toResponseDto(stage);
    }

    @Override
    public void delete(StageId id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(id);

        repository.deleteById(id);
    }

    private Stage mapToEntity(StageRequestDto dto, Competition competition) {
        return mapper.toEntity(dto)
                .setCompetition(competition);
    }

    private void validateStagesBetweenCompetitionStartAndEnd(List<Stage> stages, Competition competition) {
        boolean hasInvalidDate = stages.stream()
                .map(Stage::getDate)
                .anyMatch(date -> !DateValidator.isDateBetween(date, competition.getStartDate(), competition.getEndDate()));

        if (hasInvalidDate)
            throw new EntityCreationException("""
                    Failed to save stages
                    because there is some stage that his date not between competition dates
                    """, List.of());
    }
}
