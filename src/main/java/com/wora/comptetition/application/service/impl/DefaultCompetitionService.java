package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.CompetitionRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.service.CompetitionService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
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
public class DefaultCompetitionService implements CompetitionService {
    private final CompetitionRepository repository;
    private final ModelMapper mapper;


    @Override
    public List<CompetitionResponseDto> findAll() {
        return repository.findAll()
                .stream().map(this::toResponseDto)
                .toList();
    }

    @Override
    public CompetitionResponseDto findById(CompetitionId id) {
        return repository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public CompetitionResponseDto create(CompetitionRequestDto dto) {
        final Competition competition = repository.save(mapper.map(dto, Competition.class));
        return toResponseDto(competition);
    }

    @Override
    public CompetitionResponseDto update(CompetitionId id, CompetitionRequestDto dto) {
        final Competition competition = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        mapper.map(dto, competition);
        return toResponseDto(competition);
    }

    @Override
    public void delete(CompetitionId id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(id);
        repository.deleteById(id);
    }

    private CompetitionResponseDto toResponseDto(Competition competition) {
        return mapper.map(competition, CompetitionResponseDto.class);
    }
}
