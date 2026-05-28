package com.codigo.cowork.repository;

import com.codigo.cowork.model.Sala;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SalaRepository {

    private final List<Sala> salas = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Sala> findAll() {
        return new ArrayList<>(salas);
    }

    public Optional<Sala> findById(Long id) {
        return salas.stream()
                .filter(sala -> sala.getId().equals(id))
                .findFirst();
    }

    public Sala save(Sala sala) {
        if (sala.getId() == null) {
            // Si es nueva, genera el ID secuencial de forma segura
            sala.setId(idGenerator.getAndIncrement());
            salas.add(sala);
        } else {
            findById(sala.getId()).ifPresent(existente -> {
                existente.setCodigo(sala.getCodigo());
                existente.setNombre(sala.getNombre());
                existente.setCapacidad(sala.getCapacidad());
                existente.setUbicacion(sala.getUbicacion());
                existente.setActiva(sala.isActiva());
            });
        }
        return sala;
    }

    public void deleteById(Long id) {
        salas.removeIf(sala -> sala.getId().equals(id));
    }
}