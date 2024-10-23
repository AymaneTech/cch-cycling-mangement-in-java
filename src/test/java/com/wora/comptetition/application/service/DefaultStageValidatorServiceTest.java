package com.wora.comptetition.application.service;

import com.wora.comptetition.application.mapper.StageMapper;
import com.wora.comptetition.application.service.impl.DefaultStageValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("stage validator service")
class DefaultStageValidatorServiceTest {
    @Mock
    private StageMapper mapper;

    private StageValidatorService stageValidatorService;

    @BeforeEach
    void setup() {
        this.stageValidatorService = new DefaultStageValidatorService(mapper);
    }



}