package com.wora.comptetition.application.service.impl;

import com.wora.common.domain.exception.EntityCreationException;
import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.mapper.StageMapper;
import com.wora.comptetition.application.service.StageValidatorService;
import com.wora.comptetition.domain.entity.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultStageValidatorService implements StageValidatorService {
    private final StageMapper mapper;

    @Override

    public List<Stage> validateDtoAndGetStages(List<StageRequestDto> dtos) {
        if (dtos.isEmpty())
            return List.of();

        List<Stage> mappedStages = dtos.stream()
                .map(mapper::toEntity)
                .toList();
        return validateStages(mappedStages);
    }

    @Override
    public List<Stage> validateAndGetStages(List<Stage> stages) {
        if (stages.isEmpty())
            return List.of();

        return validateStages(stages);
    }

    private List<Stage> validateStages(List<Stage> stages) {
        List<Stage> sortedStages = stages.stream()
                .sorted(Comparator.comparing(Stage::getStageNumber))
                .toList();
        List<String> errors = new ArrayList<>();

        validateDuplicatedStageNumbers(sortedStages).ifPresent(errors::add);
        validateUniqueDates(sortedStages).ifPresent(errors::add);
        validateRoutes(sortedStages).ifPresent(errors::addAll);
        validateStageConnections(sortedStages).ifPresent(errors::addAll);

        if (!errors.isEmpty())
            throw new EntityCreationException(
                    "Failed to save the competition because of stages errors, sir tatjib data mgada o n3yto lik",
                    errors
            );

        return sortedStages;
    }

    private Optional<String> validateDuplicatedStageNumbers(List<Stage> stages) {
        Set<Integer> visitedStageNumbers = new HashSet<>();
        Set<Integer> duplicatedStageNumbers = stages.stream()
                .map(Stage::getStageNumber)
                .filter(number -> !visitedStageNumbers.add(number))
                .collect(Collectors.toSet());

        return duplicatedStageNumbers.isEmpty()
                ? Optional.empty()
                : Optional.of("Duplicate Stage Numbers Found: " + duplicatedStageNumbers);
    }

    private Optional<String> validateUniqueDates(List<Stage> stages) {
        Set<LocalDate> visitedDates = new HashSet<>();
        Set<LocalDate> duplicatedDates = stages.stream()
                .map(Stage::getDate)
                .filter(date -> !visitedDates.add(date))
                .collect(Collectors.toSet());
        return duplicatedDates.isEmpty()
                ? Optional.empty()
                : Optional.of("Duplicate Stage date Found: " + duplicatedDates);
    }

    private Optional<List<String>> validateRoutes(List<Stage> stages) {
        Set<Route> visitedRoutes = new HashSet<>();
        List<String> errors = new ArrayList<>();

        stages.stream()
                .map(stage -> new Route(stage.getStartLocation(), stage.getEndLocation()))
                .forEach(route -> {
                    if (!visitedRoutes.add(route))
                        errors.add("Duplicated Route Found: " + route.start + " -> " + route.end);

                    if (route.start.equals(route.end))
                        errors.add("Start and End locations are the same, which is not good motherfucker: " + route.start);
                });
        return errors.isEmpty()
                ? Optional.empty()
                : Optional.of(errors);
    }

    private Optional<List<String>> validateStageConnections(List<Stage> stages) {
        if (stages.size() <= 1)
            return Optional.empty();

        List<String> errors = new ArrayList<>();

        stages.stream()
                .reduce((prev, curr) -> {
                    if (!prev.getEndLocation().equals(curr.getStartLocation()))
                        errors.add("Stages are not connected. Stage " + curr.getStageResults() +
                                " Should Start at " + prev.getEndLocation());
                    return curr;
                });
        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

    record Route(String start, String end) {
    }
}
