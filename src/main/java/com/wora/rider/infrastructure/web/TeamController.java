package com.wora.rider.infrastructure.web;

import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.TeamService;
import com.wora.rider.domain.valueObject.TeamId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService service;

    @GetMapping
    public ResponseEntity<List<TeamResponseDto>> findAll() {
        List<TeamResponseDto> teams = service.findAll();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDto> findById(@PathVariable("id") UUID id) {
        TeamResponseDto team = service.findById(new TeamId(id));
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<TeamResponseDto> create(@RequestBody @Valid TeamRequestDto dto) {
        TeamResponseDto team = service.create(dto);
        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDto> update(@PathVariable("id") UUID id, @RequestBody @Valid TeamRequestDto dto) {
        TeamResponseDto team = service.update(new TeamId(id), dto);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(new TeamId(id));
        return ResponseEntity.noContent().build();
    }


}
