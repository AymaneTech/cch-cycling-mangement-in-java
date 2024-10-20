package com.wora.rider.infrastructure.web;

import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<TeamResponseDto>> findAll() {
        List<TeamResponseDto> teams = service.findAll();
        System.out.println(teams);
        return ResponseEntity.ok(teams);
    }

}
