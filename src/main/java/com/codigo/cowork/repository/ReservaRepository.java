package com.codigo.cowork.repository;

import com.codigo.cowork.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Reserva> findAll() {
        return new ArrayList<>(reservas);
    }

    public Optional<Reserva> findById(Long id) {
        return reservas.stream()
                .filter(reserva -> reserva.getId().equals(id))
                .findFirst();
    }

    public Reserva save(Reserva reserva) {
        if (reserva.getId() == null) {
            reserva.setId(idGenerator.getAndIncrement());
            reservas.add(reserva);
        } else {
            findById(reserva.getId()).ifPresent(existente -> {
                existente.setSalaId(reserva.getSalaId());
                existente.setResponsable(reserva.getResponsable());
                existente.setEmail(reserva.getEmail());
                existente.setFecha(reserva.getFecha());
                existente.setHoraInicio(reserva.getHoraInicio());
                existente.setHoraFin(reserva.getHoraFin());
                existente.setEstado(reserva.getEstado());
            });
        }
        return reserva;
    }

    public void deleteById(Long id) {
        reservas.removeIf(reserva -> reserva.getId().equals(id));
    }

    public void deleteBySalaId(Long salaId) {
        reservas.removeIf(reserva -> reserva.getSalaId().equals(salaId));
    }
}
