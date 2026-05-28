package com.codigo.cowork.controller;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerConFiltros(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) Long salaId) {
        return ResponseEntity.ok(reservaService.obtenerConFiltros(estado, fecha, salaId));
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerPorSala(@PathVariable Long salaId) {
        return ResponseEntity.ok(reservaService.obtenerConFiltros(null, null, salaId));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        return reservaService.cambiarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comprobante")
    public ResponseEntity<Map<String, Object>> subirComprobante(
            @PathVariable Long id,
            @RequestHeader("X-Cliente-Id") String clienteId,
            @RequestParam("file") MultipartFile file) {

        // Simulación de respuesta técnica solicitada
        Map<String, Object> respuesta = Map.of(
                "nombreArchivo", file.getOriginalFilename(),
                "tamañoBytes", file.getSize(),
                "clienteId", clienteId
        );
        return ResponseEntity.ok(respuesta);
    }
}