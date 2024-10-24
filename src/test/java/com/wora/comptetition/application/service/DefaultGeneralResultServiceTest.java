package com.wora.comptetition.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.GeneralResultRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.dto.response.GeneralResultResponseDto;
import com.wora.comptetition.application.mapper.GeneralResultMapper;
import com.wora.comptetition.application.service.impl.DefaultGeneralResultService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.exception.CompetitionClosedException;
import com.wora.comptetition.domain.repository.CompetitionRepository;
import com.wora.comptetition.domain.repository.GeneralResultRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.repository.RiderRepository;
import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("default general result service test")
class DefaultGeneralResultServiceTest {
    @Mock
    private GeneralResultRepository repository;
    @Mock
    private RiderRepository riderRepository;
    @Mock
    private CompetitionRepository competitionRepository;
    @Mock
    private GeneralResultMapper mapper;
    private GeneralResultService sut;
    private GeneralResultRequestDto dto;

    @BeforeEach
    void setup() {
        sut = new DefaultGeneralResultService(repository, riderRepository, competitionRepository, mapper);
        dto = new GeneralResultRequestDto(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    void subscribeToCompetition_ShouldThrowEntityNotFoundException_WhenGivenNotExistingRider() {
        when(riderRepository.findById(eq(new RiderId(dto.riderId())))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sut.subscribeToCompetition(dto));
    }

    @Test
    void subscribeToCompetition_ShouldThrowEntityNotFoundException_WhenGivenNotExistingCompetition() {
        Rider rider = new Rider(new RiderId(dto.riderId()), new Name("abdelhak", "azrour"), "marrakech", LocalDate.of(2004, 10, 27), null);

        when(riderRepository.findById(eq(new RiderId(dto.riderId())))).thenReturn(Optional.of(rider));
        when(competitionRepository.findById(eq(new CompetitionId(dto.competitionId())))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sut.subscribeToCompetition(dto));
    }

    @Test
    void subscribeToCompetition_ShouldReturnCreatedGeneralResult_WhenGivenValidRequest() {
        Rider rider = new Rider(new RiderId(dto.riderId()), new Name("abdelhak", "azrour"), "marrakech", LocalDate.of(2004, 10, 27), null);
        Competition competition = new Competition(new CompetitionId(dto.competitionId()), "maroc global", LocalDate.now(), LocalDate.now().plusMonths(1));
        GeneralResult expected = new GeneralResult(competition, rider);

        when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.of(rider));
        when(competitionRepository.findById(any(CompetitionId.class))).thenReturn(Optional.of(competition));
        when(repository.save(any(GeneralResult.class))).thenReturn(expected);
        when(mapper.toResponseDto(any(GeneralResult.class)))
                .thenReturn(new GeneralResultResponseDto(
                        new CompetitionResponseDto(competition.getId().value(), competition.getName(), competition.getStartDate(), competition.getEndDate(), List.of()),
                        new RiderResponseDto(rider.getId().value(), rider.getName(), rider.getNationality(), rider.getDateOfBirth(), null)
                ));

        GeneralResultResponseDto actual = sut.subscribeToCompetition(dto);

        assertEquals(expected.getCompetition().getName(), actual.competition().name());
        assertEquals(expected.getRider().getName(), actual.rider().name());
    }

    @Test
    void subscribeToCompetition_ShouldThrowCompetitionClosedException_WhenGivenClosedCompetition() {
        Rider rider = new Rider(new RiderId(dto.riderId()), new Name("abdelhak", "azrour"), "marrakech", LocalDate.of(2004, 10, 27), null);
        Competition competition = new Competition(new CompetitionId(dto.competitionId()), "maroc global", LocalDate.now(), LocalDate.now().plusMonths(1))
                .setClosed(true);
        GeneralResult expected = new GeneralResult(competition, rider);

        when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.of(rider));
        when(competitionRepository.findById(any(CompetitionId.class))).thenReturn(Optional.of(competition));

        assertThrows(CompetitionClosedException.class, () -> sut.subscribeToCompetition(dto));
    }
}