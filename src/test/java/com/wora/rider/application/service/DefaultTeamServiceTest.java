package com.wora.rider.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.mapper.TeamMapper;
import com.wora.rider.application.service.impl.DefaultTeamService;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.TeamId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private TeamMapper mapper;

    //    @InjectMocks
    private TeamService sut;

    @BeforeEach
    void setup() {
        this.sut = new DefaultTeamService(teamRepository, mapper);
    }

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
            when(mapper.toResponseDto(any(Team.class)))
                    .thenAnswer(invocation -> {
                        Team team = invocation.getArgument(0);
                        return new TeamResponseDto(team.getId().value(), team.getName(), team.getCountry(), List.of());
                    });

            List<TeamResponseDto> actual = sut.findAll();

            assertEquals(excepted.size(), actual.size());
            verify(teamRepository).findAll();
            verify(mapper, times(2)).toResponseDto(any(Team.class));
        }

        @DisplayName("Should return empty list when no teams exists")
        @Test
        void findAll_ShouldReturnEmptyList_WheNoTeams() {
            when(teamRepository.findAll()).thenReturn(Collections.emptyList());

            List<TeamResponseDto> actual = sut.findAll();

            assertEquals(0, actual.size());
            verify(teamRepository).findAll();
            verify(mapper, never()).toResponseDto(any(Team.class));
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
            when(mapper.toResponseDto(any(Team.class)))
                    .thenAnswer(invocation -> {
                        Team team = invocation.getArgument(0);
                        return new TeamResponseDto(team.getId().value(), team.getName(), team.getCountry(), List.of());
                    });

            TeamResponseDto actual = sut.findById(expected.getId());

            assertNotNull(actual);
            verify(teamRepository).findById(any(TeamId.class));
            verify(mapper).toResponseDto(any(Team.class));
        }

        @DisplayName("Should Throw TeamNotFoundException when given not existing id")
        @Test
        void findById_ShouldThrowTeamNotFoundException_WhenGivenNotExistingId() {
            TeamId teamId = new TeamId();
            when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.findById(teamId));
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

            when(mapper.toEntity(any(TeamRequestDto.class)))
                    .thenReturn(expected);

            when(teamRepository.save(any(Team.class)))
                    .thenReturn(expected);

            when(mapper.toResponseDto(any(Team.class)))
                    .thenReturn(new TeamResponseDto(expected.getId().value(), expected.getName(), expected.getCountry(), List.of()));

            TeamResponseDto actual = sut.create(dto);

            assertEquals(expected.getId().value(), actual.id());
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
            TeamId teamId = new TeamId();
            TeamRequestDto dto = new TeamRequestDto("new name", "new country");
            Team existingTeam = new Team(teamId, "old name", "old country");

            when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));

            when(mapper.toResponseDto(existingTeam)).thenReturn(
                    new TeamResponseDto(teamId.value(), dto.name(), dto.country(), List.of())
            );

            TeamResponseDto actual = sut.update(teamId, dto);

            assertNotNull(actual);
            assertEquals(dto.name(), actual.name());
            assertEquals(dto.country(), actual.country());

            verify(teamRepository).findById(teamId);
            verifyNoMoreInteractions(teamRepository);
        }


        @DisplayName("Should throw exception EntityNotFoundException when given not existing team Id")
        @Test
        void update_ShouldThrowEntityNotFoundException_WhenGivenNotExistingId() {
            TeamId teamId = new TeamId();
            TeamRequestDto dto = new TeamRequestDto("new name", "new country");

            when(teamRepository.findById(eq(teamId)))
                    .thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.update(teamId, dto));
        }
    }

    @DisplayName("delete() method tests")
    @Nested
    class DeleteTests {
        @DisplayName("Should delete team when given existing id")
        @Test
        void delete_ShouldDeleteTeamWhenGivenExistingId() {
            TeamId teamId = new TeamId();

            when(teamRepository.existsById(eq(teamId))).thenReturn(true);

            sut.delete(teamId);

            verify(teamRepository).deleteById(eq(teamId));
        }

        @DisplayName("Should Throw Entity Not Found Exception When Given Not Existing Id")
        @Test
        void delete_ShouldThrowEntityNotFoundExceptionWhenGivenNotExistingId() {
            TeamId teamId = new TeamId();

            when(teamRepository.existsById(eq(teamId))).thenReturn(false);

            assertThrows(EntityNotFoundException.class, () -> sut.delete(teamId));
        }
    }
}