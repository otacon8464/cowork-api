package com.codigo.cowork.service;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.mapper.ReservaMapper;
import com.codigo.cowork.model.Reserva;
import com.codigo.cowork.repository.ReservaRepository;
import com.codigo.cowork.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;

    public ReservaService(ReservaRepository reservaRepository, SalaRepository salaRepository) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
    }

    public List<ReservaResponseDTO> obtenerConFiltros(String estado, LocalDate fecha, Long salaId) {
        return reservaRepository.findAll().stream()
                .filter(r -> (estado == null || estado.isBlank() || r.getEstado().equalsIgnoreCase(estado)))
                .filter(r -> (fecha == null || r.getFecha().equals(fecha)))
                .filter(r -> (salaId == null || r.getSalaId().equals(salaId)))
                .map(ReservaMapper::toDto)
                .toList();
    }

    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        if (salaRepository.findById(dto.salaId()).isPresent()) {
                throw  new IllegalArgumentException("Sala no encontrada");
        }
        Reserva reserva = ReservaMapper.toEntity(dto);
        reserva.setSalaId(dto.salaId());
        reserva.setEstado("PENDIENTE");

        return ReservaMapper.toDto(reservaRepository.save(reserva));
    }

    public Optional<ReservaResponseDTO> cambiarEstado(Long id, String nuevoEstado) {
        String estadoUpper = nuevoEstado.toUpperCase();
        if (!List.of("PENDIENTE", "CONFIRMADA", "CANCELADA").contains(estadoUpper)) {
            throw new IllegalArgumentException("Estado inválido");
        }

        return reservaRepository.findById(id).map(reserva -> {
            reserva.setEstado(estadoUpper);
            return ReservaMapper.toDto(reservaRepository.save(reserva));
        });
    }

    public boolean eliminar(Long id) {
        return reservaRepository.findById(id).map(r -> {
            reservaRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}