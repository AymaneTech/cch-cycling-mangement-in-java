package com.wora.rider.infrastructure.web;

import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.service.RiderService;
import com.wora.rider.domain.valueObject.RiderId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RiderController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class RiderController {
    public final static String CONTROLLER_PATH = "/api/v1/riders";
    private final RiderService service;

    @GetMapping
    public ResponseEntity<List<RiderResponseDto>> findAll() {
        List<RiderResponseDto> riders = service.findAll();
        return ResponseEntity.ok(riders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiderResponseDto> findById(@PathVariable("id") UUID id) {
        RiderResponseDto rider = service.findById(new RiderId(id));
        return ResponseEntity.ok(rider);
    }

    @PostMapping
    public ResponseEntity<RiderResponseDto> create(@RequestBody @Valid RiderRequestDto dto) {
        RiderResponseDto rider = service.create(dto);
        return new ResponseEntity<>(rider, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiderResponseDto> update(@PathVariable UUID id, @RequestBody @Valid RiderRequestDto dto) {
        RiderResponseDto rider = service.update(new RiderId(id), dto);
        return ResponseEntity.ok(rider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(new RiderId(id));
        return ResponseEntity.noContent().build();
    }
}
