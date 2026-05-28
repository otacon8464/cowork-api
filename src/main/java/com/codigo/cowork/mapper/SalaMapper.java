package com.codigo.cowork.mapper;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.model.Sala;

public class SalaMapper {


    public static Sala toEntity(SalaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Sala sala = new Sala();
        sala.setCodigo(dto.codigo());
        sala.setNombre(dto.nombre());
        sala.setCapacidad(dto.capacidad());
        sala.setUbicacion(dto.ubicacion());
        return sala;
    }

    public static SalaResponseDTO toDto(Sala sala) {
        if (sala == null) {
            return null;
        }

        String descripcionCorta = sala.getCodigo() + " " + sala.getNombre() + " (cap. " + sala.getCapacidad() + ")";

        return new SalaResponseDTO(
                sala.getId(),
                sala.getCodigo(),
                sala.getNombre(),
                sala.getCapacidad(),
                sala.getUbicacion(),
                sala.isActiva(),
                descripcionCorta
        );
    }
}