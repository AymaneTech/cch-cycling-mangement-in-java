package com.wora.comptetition.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.comptetition.application.dto.request.StageResultRequestDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.application.dto.response.StageResultResponseDto;
import com.wora.comptetition.application.mapper.StageResultMapper;
import com.wora.comptetition.application.service.impl.DefaultStageResultService;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.comptetition.domain.exception.RiderNotSubscribeCompetitionException;
import com.wora.comptetition.domain.repository.StageRepository;
import com.wora.comptetition.domain.repository.StageResultRepository;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.StageId;
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

import java.time.Duration;
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
@DisplayName("default stage result service test")
class DefaultStageResultServiceTest {
    @Mock
    private StageResultRepository repository;
    @Mock
    private RiderRepository riderRepository;
    @Mock
    private StageRepository stageRepository;
    @Mock
    private StageResultMapper mapper;

    private StageResultService sut;
    private StageResultRequestDto dto;


    @BeforeEach
    void setup() {
        sut = new DefaultStageResultService(repository, riderRepository, stageRepository, mapper);
        dto = new StageResultRequestDto(UUID.randomUUID(), UUID.randomUUID(), Duration.ofHours(2));
    }

    @Test
    void savePassedStage_ShouldThrowEntityNotFoundException_WhenGivenNotExistingRider() {
        when(riderRepository.findById(eq(new RiderId(dto.riderId())))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sut.savePassedStage(dto));
    }

    @Test
    void savePassedStage_ShouldThrowEntityNotFoundException_WhenGivenNotExistingStage() {
        Rider rider = new Rider(new RiderId(dto.riderId()), new Name("abdelhak", "azrour"), "marrakech", LocalDate.of(2004, 10, 27), null);
        when(riderRepository.findById(eq(new RiderId(dto.riderId())))).thenReturn(Optional.of(rider));
        when(stageRepository.findById(eq(new StageId(dto.stageId())))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sut.savePassedStage(dto));
    }

    @Test
    void savePassedStage_ShouldThrowRiderNotSubscribeCompetitionException_WhenRiderNotJoinCompetition() {
        Rider rider = new Rider(new RiderId(dto.riderId()), new Name("abdelhak", "azrour"), "marrakech", LocalDate.of(2004, 10, 27), null);
        Stage stage = new Stage(23, 22.2, "marrakech", "safi", LocalDate.now(), null).setId(new StageId());

        StageResult expected = new StageResult(rider, stage, dto.duration());

        when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.of(rider));
        when(stageRepository.findById(any(StageId.class))).thenReturn(Optional.of(stage));

        assertThrows(RiderNotSubscribeCompetitionException.class, () -> sut.savePassedStage(dto));
    }

    @Test
    void savePassedStage_ShouldReturnCreatedStageResult_WhenGivenValidRiderAndStage() {
        Competition competition = new Competition(new CompetitionId(), "name", LocalDate.now(), LocalDate.now().plusMonths(1));
        Rider rider = new Rider(new RiderId(dto.riderId()), new Name("abdelhak", "azrour"), "marrakech", LocalDate.of(2004, 10, 27), null);
        Stage stage = new Stage(23, 22.2, "marrakech", "safi", LocalDate.now(), null)
                .setId(new StageId())
                .setCompetition(competition);
        GeneralResult generalResult = new GeneralResult(competition, rider);
        rider.setGeneralResults(List.of(generalResult));

        StageResult expected = new StageResult(rider, stage, dto.duration());


        when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.of(rider));
        when(stageRepository.findById(any(StageId.class))).thenReturn(Optional.of(stage));
        when(repository.save(any(StageResult.class))).thenReturn(expected);
        when(mapper.toResponseDto(any(StageResult.class)))
                .thenReturn(new StageResultResponseDto(
                                new StageResponseDto(stage.getId().value(), stage.getStageNumber(), stage.getDistance(), stage.getStartLocation(), stage.getEndLocation(), stage.getDate(), stage.isClosed(), null),
                                new RiderResponseDto(rider.getId().value(), rider.getName(), rider.getNationality(), rider.getDateOfBirth(), null),
                                expected.getDuration(), 1
                        )
                );
        StageResultResponseDto actual = sut.savePassedStage(dto);

        assertEquals(expected.getStage().getStageNumber(), actual.stage().stageNumber());
        assertEquals(expected.getRider().getName(), actual.rider().name());
    }

    @Test
    @DisplayName("Should throw rider not subscribe any competition this time when given rider that not joining any competition")
    void savePassedStage_ShouldThrowRiderNotSubscribeCompetitionException_WhenGivenRiderThatNotJoinAnyCopetition() {
        Competition competition = new Competition(new CompetitionId(), "name", LocalDate.now(), LocalDate.now().plusMonths(1));
        Rider rider = new Rider(new RiderId(dto.riderId()), new Name("abdelhak", "azrour"), "marrakech", LocalDate.of(2004, 10, 27), null);
        Stage stage = new Stage(23, 22.2, "marrakech", "safi", LocalDate.now(), null)
                .setId(new StageId())
                .setCompetition(competition);
        StageResult expected = new StageResult(rider, stage, dto.duration());


        when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.of(rider));
        when(stageRepository.findById(any(StageId.class))).thenReturn(Optional.of(stage));

        assertThrows(RiderNotSubscribeCompetitionException.class, () -> sut.savePassedStage(dto));
    }
}