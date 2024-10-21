package com.wora.comptetition.infrastructure.web;

import com.wora.comptetition.application.dto.request.PassedStageRequestDto;
import com.wora.comptetition.application.dto.response.PassedStageResponseDto;
import com.wora.comptetition.application.service.StageResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StageResultController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class StageResultController {
    public static final String CONTROLLER_PATH = "/api/v1/stage-results";
    private StageResultService service;

    @PostMapping
    public ResponseEntity<PassedStageResponseDto> save(@RequestBody @Valid PassedStageRequestDto dto) {
        PassedStageResponseDto result = service.savePassedStage(dto);
        return ResponseEntity.ok(result);
    }
}
