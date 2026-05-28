package com.codigo.cowork.service;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.mapper.ReservaMapper;
import com.codigo.cowork.model.Reserva;
import com.codigo.cowork.model.Sala;
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

    public Optional<ReservaResponseDTO> obtenerPorId(Long id) {
        return reservaRepository.findById(id).map(ReservaMapper::toDto);
    }

    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new IllegalArgumentException("La sala especificada no existe."));

        Reserva reserva = ReservaMapper.toEntity(dto);
        reserva.setEstado("PENDIENTE");
        return ReservaMapper.toDto(reservaRepository.save(reserva));
    }

    public Optional<ReservaResponseDTO> cambiarEstado(Long id, String nuevoEstado) {
        return reservaRepository.findById(id).map(existente -> {
            existente.setEstado(nuevoEstado.toUpperCase());
            reservaRepository.save(existente);
            return ReservaMapper.toDto(existente);
        });
    }

    public boolean eliminar(Long id) {
        if (reservaRepository.findById(id).isPresent()) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}