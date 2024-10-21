package com.wora.comptetition.application.service.impl;

import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.service.StageValidatorService;
import com.wora.comptetition.domain.entity.Stage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultStageValidatorService implements StageValidatorService {
    private final ModelMapper mapper;

    @Override
    public List<Stage> validateAndGetStages(List<StageRequestDto> dtos) {
        if (dtos.isEmpty())
            return List.of();

        return validateStages(dtos)
                .stream().map(s -> mapper.map(s, Stage.class))
                .toList();
    }

    private static List<StageRequestDto> validateStages(List<StageRequestDto> dtos) {
        List<StageRequestDto> sortedStages = dtos.stream()
                .sorted(Comparator.comparing(StageRequestDto::stageNumber))
                .toList();

        Set<LocalDate> uniqueDates = new HashSet<>();
        Set<Pair<String>> locationPairs = new HashSet<>();

        for (int i = 0; i < sortedStages.size(); i++) {
            StageRequestDto stage = sortedStages.get(i);
            String start = stage.startLocation();
            String end = stage.endLocation();

            if (!locationPairs.add(new Pair<>(start, end)))
                throw new IllegalArgumentException("duplicated route found: " + start + " -> " + end);

            if (!uniqueDates.add(stage.date()))
                throw new IllegalArgumentException("Competition has two or more stages in the same date");

            if (start.equals(end))
                throw new IllegalArgumentException("Duplicate start and end locations found " + start + " -> " + end);

            if (i > 0) {
                String previousEnd = sortedStages.get(i - 1).endLocation();
                if (!previousEnd.equals(start)) {
                    throw new IllegalArgumentException("Stages are not connected. Stage " + i + " should start at " + previousEnd);
                }
            }
        }
        return sortedStages;
    }

    record Pair<T>(T first, T second) {
    }
}
