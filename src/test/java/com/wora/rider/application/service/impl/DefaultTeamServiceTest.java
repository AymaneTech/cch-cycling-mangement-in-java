package com.wora.rider.application.service.impl;

import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.exception.TeamNotFoundException;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.TeamId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default Team service test")
class DefaultTeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DefaultTeamService sut;

    @Nested
    @DisplayName("findAll() method tests")
    class FindAllTests {

        @DisplayName("Should return all teams when exists")
        @Test
        void findAll_ShouldReturnAllTeams_WhenTeamsExists() {
            List<Team> excepted = List.of(
                    new Team(new TeamId(), "kacm", "maroc"),
                    new Team(new TeamId(), "ocs", "maroc")
            );

            when(teamRepository.findAll()).thenReturn(excepted);
            when(mapper.map(any(Team.class), eq(TeamResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Team team = invocation.getArgument(0);
                        return new TeamResponseDto(team.getId(), team.getName(), List.of());
                    });

            List<TeamResponseDto> actual = sut.findAll();

            assertEquals(excepted.size(), actual.size());
            verify(teamRepository).findAll();
            verify(mapper, times(2)).map(any(Team.class), eq(TeamResponseDto.class));
        }

        @DisplayName("Should return empty list when no teams exists")
        @Test
        void findAll_ShouldReturnEmptyList_WheNoTeams() {
            when(teamRepository.findAll()).thenReturn(Collections.emptyList());

            List<TeamResponseDto> actual = sut.findAll();

            assertEquals(0, actual.size());
            verify(teamRepository).findAll();
            verify(mapper, never()).map(any(Team.class), eq(TeamResponseDto.class));
        }
    }

    @DisplayName("findById() method tests")
    @Nested
    class FindByIdTests {

        @Test
        @DisplayName("Should return team dto when given existing id")
        void findById_ShouldReturnTeamDtoWhenGivenExistingId() {
            Team expected = new Team(new TeamId(), "real madrid", "spania");

            when(teamRepository.findById(any(TeamId.class))).thenReturn(Optional.of(expected));
            when(mapper.map(any(Team.class), eq(TeamResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Team team = invocation.getArgument(0);
                        return new TeamResponseDto(team.getId(), team.getName(), List.of());
                    });

            TeamResponseDto actual = sut.findById(expected.getId());

            assertNotNull(actual);
            verify(teamRepository).findById(any(TeamId.class));
            verify(mapper).map(any(Team.class), eq(TeamResponseDto.class));

        }

        @DisplayName("Should Throw TeamNotFoundException when given not existing id")
        @Test
        void findById_ShouldThrowTeamNotFoundException_WhenGivenNotExistingId() {
            TeamId teamId = new TeamId();
            when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

            assertThrows(TeamNotFoundException.class, () -> sut.findById(teamId));
            verify(teamRepository).findById(teamId);
        }
    }


}