package com.wora.comptetition.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.CompetitionRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
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
import org.modelmapper.ModelMapper;

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
    private ModelMapper mapper;

    //    @InjectMocks
    private CompetitionService sut;

    @BeforeEach
    void setup() {
        this.sut = new DefaultCompetitionService(repository, mapper);
    }

    @DisplayName("findAll() tests")
    @Nested
    class FindAllTests {
        @DisplayName("Should return empty list when no competition exists")
        @Test
        void findAll_ShouldReturnEmtpyList_WhenGivenNoCompetitionExists() {

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
                return new CompetitionResponseDto(new CompetitionId(), competition.getName(), competition.getStartDate(), competition.getEndDate());
            })
                    .when(mapper).map(any(Competition.class), eq(CompetitionResponseDto.class));
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
            when(mapper.map(any(Competition.class), eq(CompetitionResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Competition competition = invocation.getArgument(0);
                        return new CompetitionResponseDto(competition.getId(), competition.getName(), competition.getStartDate(), competition.getEndDate());
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
            CompetitionRequestDto expected = new CompetitionRequestDto("maroc ", LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(3));
            Competition competition = new Competition(new CompetitionId(), expected.name(), expected.startDate(), expected.endDate());

            when(mapper.map(any(CompetitionRequestDto.class), eq(Competition.class))) .thenReturn(competition);
            when(repository.save(any(Competition.class))).thenReturn(competition);
            when(mapper.map(any(Competition.class), eq(CompetitionResponseDto.class)))
                    .thenReturn(new CompetitionResponseDto(competition.getId(), competition.getName(),competition.getStartDate(), competition.getEndDate()));

            CompetitionResponseDto actual = sut.create(expected);

            assertEquals(expected.name(), actual.name());
            assertEquals(expected.startDate(), actual.startDate());
            assertTrue(actual.endDate().isAfter(actual.startDate()));
        }
    }
}