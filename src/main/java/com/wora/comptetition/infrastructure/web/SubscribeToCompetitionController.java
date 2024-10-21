package com.wora.comptetition.infrastructure.web;

import com.wora.comptetition.application.dto.request.SubscribeToCompetitionRequestDto;
import com.wora.comptetition.application.dto.response.SubscribeToCompetitionResponseDto;
import com.wora.comptetition.application.service.GeneralResultService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(SubscribeToCompetitionController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class SubscribeToCompetitionController {
    public static final String CONTROLLER_PATH = "/api/v1/subscribe-to-competition";
    private final GeneralResultService service;

    @PostMapping
    public ResponseEntity<SubscribeToCompetitionResponseDto> subscribe(@RequestBody @Valid SubscribeToCompetitionRequestDto dto) {
        SubscribeToCompetitionResponseDto result = service.subscribeToCompetition(dto);
        return ResponseEntity.ok(result);
    }
}
