package com.wora.comptetition.infrastructure.web;

import com.wora.comptetition.application.dto.request.StageResultRequestDto;
import com.wora.comptetition.application.dto.response.StageResultResponseDto;
import com.wora.comptetition.application.service.StageResultService;
import com.wora.comptetition.domain.valueObject.StageId;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(StageResultController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class StageResultController {
    public static final String CONTROLLER_PATH = "/api/v1/stage-results";
    private final StageResultService service;

    @GetMapping
    public ResponseEntity<List<StageResultResponseDto>> findAll() {
        List<StageResultResponseDto> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{stageId}/{riderId}")
    public ResponseEntity<StageResultResponseDto> findByStageIdAndRiderId(@PathVariable UUID stageId, @PathVariable UUID riderId) {
        StageResultResponseDto result = service.findByStageIdAndRiderId(new StageId(stageId), new RiderId(riderId));
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<StageResultResponseDto> save(@RequestBody @Valid StageResultRequestDto dto) {
        StageResultResponseDto result = service.savePassedStage(dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{stageId}/{riderId}")
    public ResponseEntity<Void> deleteByStageIdAndRiderId(@PathVariable UUID stageId, @PathVariable UUID riderId) {
        service.delete(new StageId(stageId), new RiderId(riderId));
        return ResponseEntity.noContent().build();
    }
}
