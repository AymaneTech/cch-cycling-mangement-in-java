package com.wora.rider.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.impl.DefaultRiderService;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.RiderRepository;
import com.wora.rider.domain.repository.TeamRepository;
import com.wora.rider.domain.valueObject.Name;
import com.wora.rider.domain.valueObject.RiderId;
import com.wora.rider.domain.valueObject.TeamId;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default Rider Service Test")
class DefaultRiderServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ModelMapper mapper;

    //    @InjectMocks
    private RiderService sut;

    private Team team;
    private Rider rider;

    @BeforeEach
    void setup() {
        sut = new DefaultRiderService(riderRepository, teamRepository, mapper);
        team = new Team(new TeamId(), "kacm", "morocco");
        rider = new Rider(new RiderId(), new Name("aymane", "el maini"), "morocco", LocalDate.now(), team);
    }

    @DisplayName("findAll() method tests")
    @Nested
    class FindAllTests {
        @DisplayName("Should return riders list when given existing riders")
        @Test
        void findAll_ShouldReturnRidersList_WhenGivenExistingRiders() {
            List<Rider> expected = List.of(
                    new Rider(new RiderId(), new Name("abdelhak", "azrour"), "morocco", LocalDate.of(2024, 10, 10), team),
                    new Rider(new RiderId(), new Name("hamza", "lamin"), "morocco", LocalDate.now(), team)
            );

            when(riderRepository.findAll()).thenReturn(expected);
            when(mapper.map(any(Rider.class), eq(RiderResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Rider rider = invocation.getArgument(0);
                        return new RiderResponseDto(
                                rider.getId(), rider.getName(), rider.getNationality(), rider.getDateOfBirth(),
                                new TeamResponseDto(rider.getTeam().getId(), rider.getTeam().getName(), rider.getTeam().getCountry(), List.of()));
                    });

            List<RiderResponseDto> actual = sut.findAll();

            assertEquals(expected.size(), actual.size());
            assertNotNull(actual);
            verify(riderRepository).findAll();
        }

        @DisplayName("Should return empty list when given no existing riders")
        @Test
        void findAll_ShouldReturnEmptyList_WhenGivenRiderNotExists() {
            when(riderRepository.findAll()).thenReturn(Collections.emptyList());

            List<RiderResponseDto> actual = sut.findAll();

            assertEquals(0, actual.size());
            assertNotNull(actual);
            verify(riderRepository).findAll();
        }
    }

    @DisplayName("findById() method tests")
    @Nested
    class FindByIdTests {
        @DisplayName("Should return existing rider when given existing id")
        @Test
        void findById_ShouldReturnExistingRiderWhenGivenExistingId() {
            when(riderRepository.findById(rider.getId())).thenReturn(Optional.of(rider));
            when(mapper.map(any(Rider.class), eq(RiderResponseDto.class)))
                    .thenReturn(new RiderResponseDto(rider.getId(), rider.getName(), rider.getNationality(), rider.getDateOfBirth(), null));

            RiderResponseDto actual = sut.findById(rider.getId());

            assertEquals(rider.getId(), actual.id());
            assertNotNull(actual);
        }

        @DisplayName("Should throw Entity not found exception when given not existing id")
        @Test
        void findById_ShouldThrowEntityNotFoundExceptionWhenGivenNotExistingId() {
            RiderId riderId = new RiderId();

            assertThrows(EntityNotFoundException.class, () -> sut.findById(riderId));
        }
    }

    @DisplayName("create() method tests")
    @Nested
    class createTests {
        @DisplayName("Should Create Rider When Given Valid Request")
        @Test
        void create_ShouldCreateRiderWhenGivenValidRequest() {
            TeamId teamId = new TeamId();
            RiderRequestDto dto = new RiderRequestDto(new Name("aymane", "el maini"), "morocco", LocalDate.of(2004, 10, 27), teamId.value());
            Rider rider = new Rider(new RiderId(), dto.name(), dto.nationality(), dto.dateOfBirth(), new Team());

            when(teamRepository.findById(any(TeamId.class))).thenReturn(Optional.of(new Team()));
            when(mapper.map(any(RiderRequestDto.class), eq(Rider.class)))
                    .thenReturn(rider);
            when(riderRepository.save(any(Rider.class))).thenReturn(rider);
            when(mapper.map(any(Rider.class), eq(RiderResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Rider r = invocation.getArgument(0);
                        return new RiderResponseDto(r.getId(), r.getName(), r.getNationality(), r.getDateOfBirth(), null);
                    });

            RiderResponseDto actual = sut.create(dto);

            assertEquals(rider.getId(), actual.id());
            assertEquals(rider.getName(), actual.name());
        }

        @DisplayName("Should throw exception when given team not found")
        @Test
        void create_ShouldThrowEntityNotFound_WhenGivenTeamNotFound() {
            TeamId teamId = new TeamId();
            RiderRequestDto dto = new RiderRequestDto(new Name("aymane", "el maini"), "morocco", LocalDate.of(2004, 10, 27), teamId.value());

            when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.create(dto));
        }

        @DisplayName("Should Throw runtime exception when repository fails to save")
        @Test
        void create_ShouldThrowRuntimeException_WhenRepositoryFailsToSave() {
            RiderRequestDto dto = new RiderRequestDto(new Name("aymane", "el maini"), "morocco", LocalDate.of(2004, 10, 27), UUID.randomUUID());

            lenient().when(teamRepository.findById(any(TeamId.class))).thenReturn(Optional.of(new Team()));

            assertThrows(RuntimeException.class, () -> sut.create(dto));
        }
    }

    @DisplayName("update() method tests")
    @Nested
    class UpdateTests {
        private RiderRequestDto dto;

        @BeforeEach
        void setup() {
            dto = new RiderRequestDto(new Name("udpated name", "udpated last name"), "Marrakech", rider.getDateOfBirth(), team.getId().value());
        }

        @DisplayName("Should return updated rider when given existing id")
        @Test
        void update_ShouldReturnUpdatedRiderWhenGivenExistingId() {
            Rider updatedRider = new Rider(rider.getId(), dto.name(), dto.nationality(), dto.dateOfBirth(), team);

            when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.of(rider));
            when(teamRepository.findById(any(TeamId.class))).thenReturn(Optional.of(team));
            doAnswer(invocation -> {
                Rider r = invocation.getArgument(1);
                r.setName(dto.name())
                        .setNationality(dto.nationality())
                        .setDateOfBirth(dto.dateOfBirth());
                return null;
            }).when(mapper).map(eq(dto), any(Rider.class));
            when(mapper.map(any(Rider.class), eq(RiderResponseDto.class)))
                    .thenReturn(new RiderResponseDto(updatedRider.getId(), updatedRider.getName(), updatedRider.getNationality(), updatedRider.getDateOfBirth(), null));

            RiderResponseDto actual = sut.update(rider.getId(), dto);

            assertEquals(rider.getId(), actual.id());
            assertEquals(rider.getName(), actual.name());
            verify(riderRepository).findById(any(RiderId.class));
        }

        @DisplayName("Should throw entity not found exception when given not existing id")
        @Test
        void udpate_ShouldThrowEntityNotFoundException_WhenGivenNotExistingId() {
            when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.findById(new RiderId()));
        }

        @DisplayName("Should throw entity not found exception when given not existing team id")
        @Test
        void update_ShouldThrowEntityNotFoundException_WhenGivenNotExistingTeamId() {
            when(riderRepository.findById(any(RiderId.class))).thenReturn(Optional.of(rider));
            when(teamRepository.findById(any(TeamId.class))).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> sut.update(new RiderId(), dto));
        }
    }

    @DisplayName("delete() method tests")
    @Nested
    class DeleteTests {
        @DisplayName("Should throw entity not found exception")
        @Test
        void delete_ShouldThrowEntityNotFoundException_WhenGivenNotExistingId() {
            when(riderRepository.existsById(any(RiderId.class))).thenReturn(false);

            assertThrows(EntityNotFoundException.class, () -> sut.delete(new RiderId()));
        }

        @DisplayName("Should delete rider when given existing id")
        @Test
        void delete_ShouldDelete_WhenGivenExistingId() {
            when(riderRepository.existsById(any(RiderId.class))).thenReturn(true);

            sut.delete(new RiderId());

            verify(riderRepository).existsById(any(RiderId.class));
            verify(riderRepository).softDeleteById(any(RiderId.class));
        }
    }
}