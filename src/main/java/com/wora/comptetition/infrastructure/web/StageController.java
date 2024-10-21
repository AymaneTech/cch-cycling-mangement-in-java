package com.wora.comptetition.infrastructure.web;

import com.wora.comptetition.application.dto.request.StageRequestDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.application.service.StageService;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.comptetition.domain.valueObject.StageId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(StageController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class StageController {
    public static final String CONTROLLER_PATH = "/api/v1/stages";
    private final StageService service;

    @GetMapping
    public ResponseEntity<List<StageResponseDto>> findAll() {
        List<StageResponseDto> stages = service.findAll();
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/competition/{id}")
    public ResponseEntity<List<StageResponseDto>> findAllByCompetitionId(@PathVariable UUID id) {
        List<StageResponseDto> stages = service.findAllByCompetitionId(new CompetitionId(id));
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StageResponseDto> findById(@PathVariable UUID id) {
        StageResponseDto stage = service.findById(new StageId(id));
        return ResponseEntity.ok(stage);
    }

    @PostMapping
    public ResponseEntity<StageResponseDto> create(@RequestBody @Valid StageRequestDto dto) {
        StageResponseDto stage = service.create(dto);
        return new ResponseEntity<>(stage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StageResponseDto> update(@PathVariable UUID id, @RequestBody @Valid StageRequestDto dto) {
        StageResponseDto stage = service.update(new StageId(id), dto);
        return ResponseEntity.ok(stage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(new StageId(id));
        return ResponseEntity.noContent().build();
    }
}
