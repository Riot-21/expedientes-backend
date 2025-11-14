package com.legaltech.crud.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legaltech.crud.dtos.CreateExpedienteDTO;
import com.legaltech.crud.dtos.ExpedienteResponseDTO;
import com.legaltech.crud.dtos.UpdateExpedienteDTO;
import com.legaltech.crud.services.ExpedienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/expediente")
@RequiredArgsConstructor
public class ExpedienteController {
    private final ExpedienteService expedienteService;

    @GetMapping
    public ResponseEntity<List<ExpedienteResponseDTO>> getAll() {
        return ResponseEntity.ok(expedienteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(expedienteService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ExpedienteResponseDTO> createExpediente(@Valid @RequestBody CreateExpedienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expedienteService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpedienteResponseDTO> updateExp(@PathVariable UUID id,
            @Valid @RequestBody UpdateExpedienteDTO dto) {
        return ResponseEntity.ok(expedienteService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        expedienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
