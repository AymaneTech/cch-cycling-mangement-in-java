package com.wora.comptetition.infrastructure.web;

import com.wora.comptetition.application.dto.request.GeneralResultRequestDto;
import com.wora.comptetition.application.dto.response.GeneralResultResponseDto;
import com.wora.comptetition.application.service.GeneralResultService;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(GeneralResultController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class GeneralResultController {
    public static final String CONTROLLER_PATH = "/api/v1/general-results";
    private final GeneralResultService service;


    @GetMapping
    public ResponseEntity<List<GeneralResultResponseDto>> findAll() {
        List<GeneralResultResponseDto> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{competitionId}/{riderId}")
    public ResponseEntity<GeneralResultResponseDto> findByCompetitionIdAndRiderId(@PathVariable UUID competitionId, @PathVariable UUID riderId) {
        GeneralResultResponseDto result = service.findByCompetitionIdAndRiderId(new CompetitionId(competitionId), new RiderId(riderId));
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<GeneralResultResponseDto> subscribe(@RequestBody @Valid GeneralResultRequestDto dto) {
        GeneralResultResponseDto result = service.subscribeToCompetition(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{competitionId}/{riderId}")
    public ResponseEntity<Void> deleteByCompetitionIdAndRiderId(@PathVariable UUID competitionId, @PathVariable UUID riderId) {
        service.delete(new CompetitionId(competitionId), new RiderId(riderId));
        return ResponseEntity.noContent().build();
    }

}
