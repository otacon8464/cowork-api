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
                .map(ReservaMapper::toDto) // Ahora vuelve a funcionar correctamente
                .toList();
    }

    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        // Validación: la sala debe existir
        salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada"));

        if (existeReservaEnFecha(dto.salaId(), dto.fecha())) {
            throw new IllegalStateException("La sala ya tiene una reserva para esta fecha");
        }

        Reserva reserva = ReservaMapper.toEntity(dto);
        reserva.setSalaId(dto.salaId());
        reserva.setEstado("PENDIENTE");

        return ReservaMapper.toDto(reservaRepository.save(reserva));
    }

    public Optional<ReservaResponseDTO> cambiarEstado(Long id, String nuevoEstado) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setEstado(nuevoEstado.toUpperCase());
            return ReservaMapper.toDto(reservaRepository.save(reserva));
        });
    }

    public boolean existeReservaEnFecha(Long salaId, LocalDate fecha) {
        return reservaRepository.findAll().stream()
                .anyMatch(r -> r.getSalaId().equals(salaId) && r.getFecha().equals(fecha));
    }

    public Optional<ReservaResponseDTO> buscarPorId(Long id) {
        return reservaRepository.findById(id).map(ReservaMapper::toDto);
    }

    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }
}