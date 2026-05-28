package com.codigo.cowork.service;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.mapper.SalaMapper;
import com.codigo.cowork.model.Sala;
import com.codigo.cowork.repository.ReservaRepository;
import com.codigo.cowork.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public SalaService(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<SalaResponseDTO> obtenerTodas() {
        return salaRepository.findAll().stream()
                .map(SalaMapper::toDto)
                .toList();
    }

    public Optional<SalaResponseDTO> obtenerPorId(Long id) {
        return salaRepository.findById(id)
                .map(SalaMapper::toDto);
    }

    public SalaResponseDTO crear(SalaRequestDTO dto) {
        Sala sala = SalaMapper.toEntity(dto);
        sala.setActiva(true);

        Sala guardada = salaRepository.save(sala);
        return SalaMapper.toDto(guardada);
    }

    public Optional<SalaResponseDTO> actualizar(Long id, SalaRequestDTO dto) {
        return salaRepository.findById(id).map(existente -> {
            existente.setCodigo(dto.codigo());
            existente.setNombre(dto.nombre());
            existente.setCapacidad(dto.capacidad());
            existente.setUbicacion(dto.ubicacion());

            Sala actualizada = salaRepository.save(existente);
            return SalaMapper.toDto(actualizada);
        });
    }

    public boolean eliminar(Long id) {
        Optional<Sala> salaOpt = salaRepository.findById(id);
        if (salaOpt.isPresent()) {
            reservaRepository.deleteBySalaId(id);
            salaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}