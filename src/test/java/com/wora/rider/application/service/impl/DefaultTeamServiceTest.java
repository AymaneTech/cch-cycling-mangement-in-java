package com.wora.rider.application.service.impl;

import com.wora.rider.application.dto.request.TeamRequestDto;
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
            when(mapper.map(any(Team.class), TeamResponseDto.class))
                    .thenAnswer(invocation -> {
                        Team team = invocation.getArgument(0);
                        return new TeamResponseDto(team.getId(), team.getName(), team.getCountry(), List.of());
                    });

            List<TeamResponseDto> actual = sut.findAll();

            assertEquals(excepted.size(), actual.size());
            verify(teamRepository).findAll();
            verify(mapper, times(2)).map(any(Team.class), TeamResponseDto.class);
        }

        @DisplayName("Should return empty list when no teams exists")
        @Test
        void findAll_ShouldReturnEmptyList_WheNoTeams() {
            when(teamRepository.findAll()).thenReturn(Collections.emptyList());

            List<TeamResponseDto> actual = sut.findAll();

            assertEquals(0, actual.size());
            verify(teamRepository).findAll();
            verify(mapper, never()).map(any(Team.class), TeamResponseDto.class);
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
            when(mapper.map(any(Team.class), TeamResponseDto.class))
                    .thenAnswer(invocation -> {
                        Team team = invocation.getArgument(0);
                        return new TeamResponseDto(team.getId(), team.getName(), team.getCountry(), List.of());
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

    @DisplayName("create() method tests")
    @Nested
    class CreateTests {
        @DisplayName("Should create team when given valid team dto")
        @Test
        void create_ShouldReturnCreatedTeamWhenGivenValidTeamDto() {
            TeamRequestDto dto = new TeamRequestDto("marrakech", "morocco");
            Team expected = new Team(new TeamId(), dto.name(), dto.country());

            when(mapper.map(any(TeamRequestDto.class), eq(Team.class)))
                    .thenReturn(expected);

            when(teamRepository.save(any(Team.class)))
                    .thenReturn(expected);

            when(mapper.map(any(Team.class), TeamResponseDto.class))
                    .thenReturn(new TeamResponseDto(expected.getId(), expected.getName(), expected.getCountry(), List.of()));

            TeamResponseDto actual = sut.create(dto);

            assertEquals(expected.getId(), actual.id());
            assertNotNull(actual);
            verify(teamRepository).save(any(Team.class));
        }

        @DisplayName("Should Throw runtime exception when repository fails to save")
        @Test
        void create_ShouldThrowRuntimeException_WhenRepositoryFailsToSave() {
            TeamRequestDto dto = new TeamRequestDto("marrakech", "morocco");
            Team expected = new Team(new TeamId(), dto.name(), dto.country());

            when(teamRepository.save(expected))
                    .thenThrow(RuntimeException.class);

            assertThrows(RuntimeException.class, () -> sut.create(dto));
        }
    }

    @DisplayName("Update method tests")
    @Nested
    class UpdateTests {
        @DisplayName("Should return updated team when given valid id and request dto")
        @Test
        void update_ShouldReturnUpdatedTeam_WhenGivenExistingIdAndRequestDto() {
            TeamRequestDto dto = new TeamRequestDto("Marrakech boys", "Morocco");
            TeamId teamId = new TeamId(); // Assuming this is properly initialized
            Team expected = new Team(teamId, dto.name(), dto.country());

            when(teamRepository.findById(teamId)).thenReturn(Optional.of(expected));

            when(mapper.map(dto, Team.class)).thenReturn(expected);

            expected.setCountry("updated country");
            expected.setName("updated name");

            when(teamRepository.save(expected)).thenReturn(expected);

            when(mapper.map(any(Team.class), eq(TeamResponseDto.class)))
                    .thenReturn(new TeamResponseDto(teamId, expected.getName(), expected.getCountry(), List.of()));

            TeamResponseDto actual = sut.update(teamId, dto);

            assertEquals(expected.getId(), actual.id());
            assertEquals("updated name", actual.name());
            assertEquals("updated country", actual.country());
        }


    }
}