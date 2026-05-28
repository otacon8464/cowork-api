package com.codigo.cowork.controller;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> crear(@RequestBody SalaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salaService.crear(dto));
    }

    @GetMapping
    public List<SalaResponseDTO> obtenerTodas() {
        return salaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public SalaResponseDTO obtenerPorId(@PathVariable Long id) {
        return salaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
    }

    @PutMapping("/{id}")
    public SalaResponseDTO actualizar(@PathVariable Long id, @RequestBody SalaRequestDTO dto) {
        return salaService.actualizar(id, dto)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        salaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}