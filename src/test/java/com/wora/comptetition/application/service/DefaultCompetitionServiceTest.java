package com.wora.comptetition.application.service;

import com.wora.common.domain.exception.EntityCreationException;
import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.CompetitionRequestDto;
import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.mapper.CompetitionMapper;
import com.wora.comptetition.application.service.impl.DefaultCompetitionService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default competition service tests")
class DefaultCompetitionServiceTest {

    @Mock
    private CompetitionRepository repository;
    @Mock
    private StageValidatorService stageValidatorService;
    @Mock
    private CompetitionMapper mapper;

    private CompetitionService sut;

    @BeforeEach
    void setup() {
        this.sut = new DefaultCompetitionService(repository, stageValidatorService, mapper);
    }

    @DisplayName("findAll() tests")
    @Nested
    class FindAllTests {
        @DisplayName("Should return empty list when no competition exists")
        @Test
        void findAll_ShouldReturnEmptyList_WhenGivenNoCompetitionExists() {

            when(repository.findAll()).thenReturn(List.of());

            List<CompetitionResponseDto> actual = sut.findAll();

            assertNotNull(actual);
            assertEquals(0, actual.size());
            verify(repository).findAll();
        }

        @DisplayName("Should return competition list when they exist")
        @Test
        void findAll_ShouldReturnCompetitionList_WhenTheyExist() {
            List<Competition> expected = List.of(
                    new Competition("tawaf maroc", LocalDate.now(), LocalDate.now().plusDays(21)),
                    new Competition("tour du france", LocalDate.now(), LocalDate.now().plusDays(23))
            );

            doAnswer(invocation -> {
                Competition competition = invocation.getArgument(0);
                return new CompetitionResponseDto(new CompetitionId().value(), competition.getName(), competition.getStartDate(), competition.getEndDate(), List.of(), List.of());
            })
                    .when(mapper).toResponseDto(any(Competition.class));
            when(repository.findAll()).thenReturn(expected);

            List<CompetitionResponseDto> actual = sut.findAll();

            assertEquals(expected.size(), actual.size());
            verify(repository).findAll();
        }
    }

    @DisplayName("findById() method tests")
    @Nested
    class FindById {
        @DisplayName("Shou ld throw entity not found exception when given id not found")
        @Test
        void findById_ShouldThrowEntityNotFoundExceptionWhenGivenIdNotFound() {

            when(repository.findById(any(CompetitionId.class))).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.findById(new CompetitionId()));
        }

        @DisplayName("Should return competition when given existing id")
        @Test
        void findById_ShouldReturnCompetition_WhenGivenExistingId() {
            Competition expected = new Competition(new CompetitionId(), "tawaf maroc", LocalDate.now(), LocalDate.now().plusDays(21));

            when(repository.findById(any(CompetitionId.class))).thenReturn(Optional.of(expected));
            when(mapper.toResponseDto(any(Competition.class)))
                    .thenAnswer(invocation -> {
                        Competition competition = invocation.getArgument(0);
                        return new CompetitionResponseDto(competition.getId().value(), competition.getName(), competition.getStartDate(), competition.getEndDate(), List.of(), List.of());
                    });

            CompetitionResponseDto actual = sut.findById(expected.getId());

            assertNotNull(actual);
            assertEquals(expected.getName(), actual.name());
            verify(repository).findById(any(CompetitionId.class));
        }
    }

    @DisplayName("create() method tests")
    @Nested
    class CreateTests {
        @DisplayName("Should return create competition when given valid competition request")
        @Test
        void create_ShouldReturnCreatedCompetition_WhenGivenValidCompetitionRequest() {
            StageRequestDto stageRequestDto = new StageRequestDto(1, 39.2, "marrakech", "safi", LocalDate.now().plusDays(3), null);
            CompetitionRequestDto expected = new CompetitionRequestDto("maroc ", LocalDate.now().plusDays(2), LocalDate.now().plusDays(21), List.of(stageRequestDto));
            Competition competition = new Competition(new CompetitionId(), expected.name(), expected.startDate(), expected.endDate());

            when(mapper.toEntity(any(CompetitionRequestDto.class))).thenReturn(competition);
            when(repository.save(any(Competition.class))).thenReturn(competition);
            when(mapper.toResponseDto(any(Competition.class)))
                    .thenReturn(new CompetitionResponseDto(competition.getId().value(), competition.getName(), competition.getStartDate(), competition.getEndDate(), List.of(), List.of()));

            CompetitionResponseDto actual = sut.create(expected);

            assertEquals(expected.name(), actual.name());
            assertEquals(expected.startDate(), actual.startDate());
            assertTrue(actual.endDate().isAfter(actual.startDate()));
        }

        @Test
        void create_ShouldThrowEntityCreationFailedException() {
            StageRequestDto stageRequestDto = new StageRequestDto(1, 39.2, "marrakech", "safi", LocalDate.now().minusWeeks(3), null);
            CompetitionRequestDto expected = new CompetitionRequestDto("maroc ", LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(3), List.of(stageRequestDto));
            Competition competition = new Competition(new CompetitionId(), expected.name(), expected.startDate(), expected.endDate());

            when(mapper.toEntity(any(CompetitionRequestDto.class))).thenReturn(competition);

            assertThrows(EntityCreationException.class, () -> sut.create(expected));
        }
    }

    @DisplayName("update() method tests")
    @Nested
    class UpdateTests {
        CompetitionRequestDto dto = new CompetitionRequestDto("competition 1", LocalDate.now(), LocalDate.now().plusMonths(1), List.of());

        @Test
        @DisplayName("Should throw entity not found exception when given not existing id")
        void update_ShouldThrowEntityNotFoundException_WhenGivenNotExistingId() {
            CompetitionId competitionId = new CompetitionId();

            when(repository.findById(eq(competitionId))).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.update(competitionId, dto));
        }

        @Test
        void update_ShouldReturnUpdatedCompetition_WhenGivenExistingId() {
            Competition competition = new Competition(new CompetitionId(), "old info", dto.startDate(), dto.endDate());

            when(repository.findById(any(CompetitionId.class))).thenReturn(Optional.of(competition));
            when(mapper.toResponseDto(any(Competition.class)))
                    .thenReturn(new CompetitionResponseDto(competition.getId().value(), dto.name(), competition.getStartDate(), competition.getEndDate(), List.of(), List.of()));

            CompetitionResponseDto actual = sut.update(competition.getId(), dto);

            assertEquals(competition.getName(), actual.name());
            assertEquals(competition.getStartDate(), actual.startDate());
            assertTrue(actual.startDate().isBefore(actual.endDate()));
        }
    }

    @DisplayName("delete() method tests")
    @Nested
    class DeleteTests {
        @Test
        void delete_ShouldThrowEntityNotFoundException_WhenGivenNotExistingId() {
            CompetitionId competitionId = new CompetitionId();

            when(repository.existsById(eq(competitionId))).thenReturn(false);

            assertThrows(EntityNotFoundException.class, () -> sut.delete(competitionId));
        }

        @Test
        void delete_ShouldDeleteCompetition_WhenGivenExistingId() {
            CompetitionId competitionId = new CompetitionId();

            when(repository.existsById(eq(competitionId))).thenReturn(true);

            sut.delete(competitionId);

            verify(repository).existsById(eq(competitionId));
            verify(repository).deleteById(eq(competitionId));
        }
    }
}