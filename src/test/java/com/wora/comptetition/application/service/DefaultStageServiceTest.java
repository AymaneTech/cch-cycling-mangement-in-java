package com.wora.comptetition.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.application.service.impl.DefaultStageService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.repository.StageRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.StageId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default stage service")
class DefaultStageServiceTest {

    @Mock
    private StageRepository repository;
    @Mock
    private CompetitionRepository competitionRepository;
    @Mock
    private ModelMapper mapper;

    private StageService sut;

    private Stage stage;
    private Competition competition;

    @BeforeEach
    void setup() {
        this.sut = new DefaultStageService(repository, competitionRepository, mapper);
        stage = new Stage(1, 30.3, "marrakech", "casablanca", LocalDate.now(), competition);
        competition = new Competition(new CompetitionId(), "maroc", LocalDate.now(), LocalDate.now().plusMonths(1));
    }

    @DisplayName("findAll() method tests")
    @Nested
    class FindAll {
        @DisplayName("Should return empty list when given no stages found")
        @Test
        void findAll_ShouldReturnEmptyList_WhenGivenNoStagesExists() {
            when(repository.findAll()).thenReturn(Collections.emptyList());

            List<StageResponseDto> actual = sut.findAll();

            assertEquals(0, actual.size());
        }

        @DisplayName("Should return stages list when they exist")
        @Test
        void findAll_ShouldReturnStagesList_WhenTheyExist() {
            List<Stage> expected = List.of(
                    new Stage(1, 30.3, "marrakech", "casablanca", LocalDate.now(), competition),
                    new Stage(2, 30.3, "casablanca", "rabat", LocalDate.now(), competition)
            );

            when(repository.findAll()).thenReturn(expected);
            when(mapper.map(any(Stage.class), eq(StageResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Stage stage = invocation.getArgument(0);
                        return new StageResponseDto(stage.getId(), stage.getStageNumber(), stage.getDistance(),
                                stage.getStartLocation(), stage.getEndLocation(), stage.getDate(),
                                new CompetitionResponseDto(competition.getId(), competition.getName(),
                                        competition.getStartDate(), competition.getEndDate()));
                    });

            List<StageResponseDto> actual = sut.findAll();

            assertEquals(2, actual.size());
            assertNotNull(actual);
        }
    }

    @DisplayName("findAllByCompetitionId() method tests")
    @Nested
    class FindAllByCompetitionId {
        @DisplayName("Should return list of stages of given competition")
        @Test
        void findAllByCompetitionId_ShouldReturnStages_WhenGivenCompetitionId() {
            List<Stage> expected = List.of(
                    new Stage(1, 30.3, "marrakech", "casablanca", LocalDate.now(), competition),
                    new Stage(2, 30.3, "casablanca", "rabat", LocalDate.now(), competition)
            );

            when(repository.findAllByCompetitionId(any(CompetitionId.class))).thenReturn(expected);
            when(mapper.map(any(Stage.class), eq(StageResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Stage stage = invocation.getArgument(0);
                        return new StageResponseDto(stage.getId(), stage.getStageNumber(), stage.getDistance(),
                                stage.getStartLocation(), stage.getEndLocation(), stage.getDate(),
                                new CompetitionResponseDto(competition.getId(), competition.getName(),
                                        competition.getStartDate(), competition.getEndDate()));
                    });

            List<StageResponseDto> actual = sut.findAllByCompetitionId(competition.getId());

            assertEquals(2, actual.size());
            verify(repository).findAllByCompetitionId(any(CompetitionId.class));
        }
    }

    @DisplayName("findById() method tests")
    @Nested
    class FindByIdTests {
        @DisplayName("Should throw entity not found exception when entity not found")
        @Test
        void findById_ShouldThrowEntityNotFoundExceptionWhenGivenNotExistingId() {
            StageId stageId = new StageId();

            assertThrows(EntityNotFoundException.class, () -> sut.findById(stageId));
        }

        @DisplayName("Should return existing stage when given existing id")
        @Test
        void findById_ShouldReturnExistingRiderWhenGivenExistingId() {
            when(repository.findById(stage.getId())).thenReturn(Optional.of(stage));
            when(mapper.map(any(Stage.class), eq(StageResponseDto.class)))
                    .thenReturn(new StageResponseDto(stage.getId(), stage.getStageNumber(), stage.getDistance(), stage.getStartLocation(),
                            stage.getEndLocation(), stage.getDate(), new CompetitionResponseDto(competition.getId(), competition.getName(),
                            competition.getStartDate(), competition.getEndDate())));

            StageResponseDto actual = sut.findById(stage.getId());

            assertEquals(stage.getId(), actual.id());
            assertNotNull(actual);
        }
    }
}