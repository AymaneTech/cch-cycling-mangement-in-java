package com.wora.comptetition.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.StageRequestDto;
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
import static org.mockito.Mockito.*;

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
        stage = new Stage(1, 30.3, "marrakech", "casablanca", LocalDate.now(), competition).setId(new StageId());
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

    @DisplayName("create() method tests")
    @Nested
    class CreateTests {
        private StageRequestDto dto;

        @BeforeEach
        void setup() {
            dto = new StageRequestDto(23, 202.2, "marrakech", "safi", LocalDate.now(), competition.getId().value());
        }

        @Test
        void create_ShouldThrowEntityNotFoundException_WhenGivenNotExistingCompetitionId() {

            when(competitionRepository.findById(any(CompetitionId.class))).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.create(dto));
        }

        @Test
        void create_ShouldReturnCreatedStage_WhenGivenValidRequest() {
            when(competitionRepository.findById(any(CompetitionId.class))).thenReturn(Optional.of(competition));
            when(mapper.map(any(StageRequestDto.class), eq(Stage.class))).thenReturn(stage);
            when(repository.save(any(Stage.class))).thenReturn(stage);
            when(mapper.map(any(Stage.class), eq(StageResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Stage s = invocation.getArgument(0);
                        return new StageResponseDto(s.getId(), s.getStageNumber(), s.getDistance(), s.getStartLocation(), s.getEndLocation(),
                                s.getDate(), new CompetitionResponseDto(competition.getId(), competition.getName(),
                                competition.getStartDate(), competition.getEndDate()));
                    });

            StageResponseDto actual = sut.create(dto);

            assertNotNull(stage);
            assertEquals(stage.getStageNumber(), actual.stageNumber());
        }
    }

    @DisplayName("update() methods tests")
    @Nested
    class UpdateTests {
        private StageRequestDto dto;

        @BeforeEach
        void setup() {
            dto = new StageRequestDto(1, 210.5, "Paris", "Marseille", LocalDate.now().plusDays(1), competition.getId().value());
        }

        @DisplayName("Should return updated stage when given existing id")
        @Test
        void update_ShouldReturnUpdatedStageWhenGivenExistingId() {
            Stage updatedStage = new Stage(dto.stageNumber(), dto.distance(), dto.startLocation(), dto.endLocation(), dto.date(), competition)
                    .setId(stage.getId());

            when(repository.findById(stage.getId())).thenReturn(Optional.of(stage));
            when(competitionRepository.findById(any(CompetitionId.class))).thenReturn(Optional.of(competition));
            doAnswer(invocation -> {
                Stage s = invocation.getArgument(1);
                s.setStageNumber(dto.stageNumber())
                        .setDistance(dto.distance())
                        .setStartLocation(dto.startLocation())
                        .setEndLocation(dto.endLocation())
                        .setDate(dto.date());
                return null;
            }).when(mapper).map(eq(dto), any(Stage.class));
            when(mapper.map(any(Stage.class), eq(StageResponseDto.class)))
                    .thenReturn(new StageResponseDto(updatedStage.getId(), updatedStage.getStageNumber(), updatedStage.getDistance(),
                            updatedStage.getStartLocation(), updatedStage.getEndLocation(), updatedStage.getDate(),
                            new CompetitionResponseDto(competition.getId(), competition.getName(), competition.getStartDate(), competition.getEndDate())));

            StageResponseDto actual = sut.update(stage.getId(), dto);

            assertEquals(stage.getId(), actual.id());
            assertEquals(dto.stageNumber(), actual.stageNumber());
            assertEquals(dto.distance(), actual.distance());
            assertEquals(dto.startLocation(), actual.startLocation());
            assertEquals(dto.endLocation(), actual.endLocation());
            assertEquals(dto.date(), actual.date());
            verify(repository).findById(any(StageId.class));
        }

        @DisplayName("Should throw entity not found exception when given not existing id")
        @Test
        void update_ShouldThrowEntityNotFoundException_WhenGivenNotExistingId() {
            when(repository.findById(any(StageId.class))).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> sut.update(new StageId(), dto));
        }

        @DisplayName("Should throw entity not found exception when given not existing competition id")
        @Test
        void update_ShouldThrowEntityNotFoundException_WhenGivenNotExistingCompetitionId() {
            when(repository.findById(any(StageId.class))).thenReturn(Optional.of(stage));
            when(competitionRepository.findById(any(CompetitionId.class))).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> sut.update(new StageId(), dto));
        }
    }

    @DisplayName("delete method tests")
    @Nested
    class DeleteTests {
        @Test
        void delete_ShouldThrowEntityNotFoundException_WhenGivenNotExistingId() {
            StageId stageId = new StageId();

            when(repository.existsById(any(StageId.class))).thenReturn(false);

            assertThrows(EntityNotFoundException.class, () -> sut.delete(stageId));
        }

        @Test
        void delete_ShouldDeleteStage_WhenGivenExistingId() {
            StageId stageId = new StageId();

            when(repository.existsById(any(StageId.class))).thenReturn(true);

            sut.delete(stageId);

            verify(repository).existsById(any(StageId.class));
            verify(repository).softDeleteById(any(StageId.class));

        }
    }
}