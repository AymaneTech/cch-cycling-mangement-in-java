package com.wora.comptetition.infrastructure.web;

import com.wora.comptetition.application.dto.request.CompetitionRequestDto;
import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.service.CompetitionService;
import com.wora.comptetition.domain.valueObject.CompetitionId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(CompetitionController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class CompetitionController {
    public final static String CONTROLLER_PATH = "/api/v1/competitions";
    private final CompetitionService service;

    @GetMapping
    public ResponseEntity<List<CompetitionResponseDto>> findAll() {
        List<CompetitionResponseDto> competitions = service.findAll();
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionResponseDto> findById(@PathVariable UUID id) {
        CompetitionResponseDto competition = service.findById(new CompetitionId(id));
        return ResponseEntity.ok(competition);
    }

    @PostMapping
    public ResponseEntity<CompetitionResponseDto> create(@RequestBody @Valid CompetitionRequestDto dto) {
        CompetitionResponseDto competition = service.create(dto);
        return new ResponseEntity<>(competition, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetitionResponseDto> update(@PathVariable UUID id, @RequestBody @Valid CompetitionRequestDto dto) {
        CompetitionResponseDto competition = service.update(new CompetitionId(id), dto);
        return ResponseEntity.ok(competition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(new CompetitionId(id));
        return ResponseEntity.noContent().build();
    }
}
