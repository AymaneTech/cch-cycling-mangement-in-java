package com.wora.rider.application.service;

import com.wora.common.domain.exception.EntityNotFoundException;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.impl.DefaultRiderService;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.entity.Team;
import com.wora.rider.domain.repository.RiderRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default Rider Service Test")
class DefaultRiderServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @Mock
    private ModelMapper mapper;

    //    @InjectMocks
    private RiderService sut;

    @BeforeEach
    void setup() {
        sut = new DefaultRiderService(riderRepository, mapper);
    }

    @DisplayName("findAll() method tests")
    @Nested
    class FindAllTests {
        @DisplayName("Should return riders list when given existing riders")
        @Test
        void findAll_ShouldReturnRidersList_WhenGivenExistingRiders() {
            Team team = new Team(new TeamId(), "kacm", "morocco");
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
            Rider expected = new Rider(new RiderId(), new Name("aymane", "el maini"), "morocco", LocalDate.now(), new Team());

            when(riderRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
            when(mapper.map(any(Rider.class), eq(RiderResponseDto.class)))
                    .thenReturn(new RiderResponseDto(expected.getId(), expected.getName(), expected.getNationality(), expected.getDateOfBirth(), null));

            RiderResponseDto actual = sut.findById(expected.getId());

            assertEquals(expected.getId(), actual.id());
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
            RiderRequestDto dto = new RiderRequestDto(new Name("aymane", "el maini"), "morocco", LocalDate.of(2004, 10, 27), UUID.randomUUID());
            Rider expected = new Rider(new RiderId(), dto.name(), dto.nationality(), dto.dateOfBirth(), new Team());

            when(riderRepository.save(any(Rider.class))).thenReturn(expected);
            when(mapper.map(any(Rider.class), eq(RiderResponseDto.class)))
                    .thenAnswer(invocation -> {
                        Rider rider = invocation.getArgument(0);
                        return new RiderResponseDto(rider.getId(), rider.getName(), rider.getNationality(), rider.getDateOfBirth(), null);
                    });

            RiderResponseDto actual = sut.create(dto);

            assertEquals(expected.getId(), actual.id());
            assertEquals(expected.getName(), actual.name());
        }
    }
}