package com.wora.comptetition.application.service;

import com.wora.common.domain.exception.EntityCreationException;
import com.wora.comptetition.application.mapper.StageMapper;
import com.wora.comptetition.application.service.impl.DefaultStageValidatorService;
import com.wora.comptetition.domain.entity.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("stage validator service")
class DefaultStageValidatorServiceTest {
    @Mock
    private StageMapper mapper;

    private StageValidatorService sut;

    @BeforeEach
    void setup() {
        this.sut = new DefaultStageValidatorService(mapper);
    }

    @Test
    void validateAndGetStages_ShouldReturnEmptyList_WhenGivenEmptyList() {
        List<Stage> actual = sut.validateAndGetStages(List.of());

        assertEquals(0, actual.size());
    }

    @Test
    void validateDuplicatedStageNumbers() {
        List<Stage> stages = List.of(
                new Stage(1, 22.2, "marrakech", "safi", LocalDate.now(), null),
                new Stage(1, 22.2, "safi", "hey", LocalDate.now().plusDays(12), null)
        );

        assertThrows(EntityCreationException.class, () -> sut.validateAndGetStages(stages));
    }

    @Test
    void validateDuplicatedDates() {
        List<Stage> stages = List.of(
                new Stage(2, 22.2, "marrakech", "safi", LocalDate.now(), null),
                new Stage(1, 22.2, "safi", "hey", LocalDate.now(), null)
        );
        assertThrows(EntityCreationException.class, () -> sut.validateAndGetStages(stages));
    }

    @Test
    void validateDuplicatedRoutes() {
        List<Stage> stages = List.of(
                new Stage(2, 22.2, "marrakech", "safi", LocalDate.now(), null),
                new Stage(1, 22.2, "marrakech", "safi", LocalDate.now().plusDays(3), null)
        );
        assertThrows(EntityCreationException.class, () -> sut.validateAndGetStages(stages));
    }

    @Test
    void validateStartAndEndLocationsAreTheSame() {
        List<Stage> stages = List.of(
                new Stage(2, 22.2, "marrakech", "marrakech", LocalDate.now(), null)
        );
        assertThrows(EntityCreationException.class, () -> sut.validateAndGetStages(stages));
    }

    @Test
    void validateAndGetStages_ShouldReturnStagesList_WhenGivenValidStages() {
        List<Stage> stages = List.of(
                new Stage(1, 22.2, "marrakech", "safi", LocalDate.now(), null),
                new Stage(2, 22.2, "safi", "el jadida", LocalDate.now().plusDays(3), null)
        );
        List<Stage> actual = sut.validateAndGetStages(stages);

        assertEquals(stages.size(), actual.size());
        assertEquals(stages.get(0), actual.get(0));
    }
}