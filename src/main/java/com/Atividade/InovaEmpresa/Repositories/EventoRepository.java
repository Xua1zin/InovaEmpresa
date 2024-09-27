package com.Atividade.InovaEmpresa.Repositories;

import com.Atividade.InovaEmpresa.entities.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<EventoEntity, Long> {
    @Query(value = "SELECT * FROM evento WHERE ?1 BETWEEN data_inicio AND data_fim", nativeQuery = true)
    Optional<EventoEntity> findEventoAtual(Instant dataAtual);

    @Query(value = "SELECT * FROM evento WHERE ?1 >= data_avaliacao_jurado", nativeQuery = true)
    Optional<EventoEntity> findEventoJurado(Instant dataAtual);

    @Query(value = "SELECT * FROM evento WHERE ?1 >= data_avaliacao_popular", nativeQuery = true)
    Optional<EventoEntity> findEventoPopular(Instant dataAtual);
}

