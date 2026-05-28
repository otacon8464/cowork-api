package com.codigo.cowork.mapper;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.model.Reserva;

public class ReservaMapper {

    public static Reserva toEntity(ReservaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Reserva reserva = new Reserva();
        reserva.setSalaId(dto.salaId());
        reserva.setResponsable(dto.responsable());
        reserva.setEmail(dto.email());
        reserva.setFecha(dto.fecha());
        reserva.setHoraInicio(dto.horaInicio());
        reserva.setHoraFin(dto.horaFin());
        return reserva;
    }

    public static ReservaResponseDTO toDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getSalaId(),
                reserva.getResponsable(),
                reserva.getEmail(),
                reserva.getFecha(),
                reserva.getHoraInicio(),
                reserva.getHoraFin(),
                reserva.getEstado()
        );
    }
}