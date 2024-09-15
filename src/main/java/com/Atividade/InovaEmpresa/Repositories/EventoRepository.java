package com.Atividade.InovaEmpresa.Repositories;

import com.Atividade.InovaEmpresa.entities.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<EventoEntity, Long> {
    @Query(value = "SELECT * FROM evento WHERE dataAtual BETWEEN dataInicio AND dataFim", nativeQuery = true)
    Optional<EventoEntity> findEventoAtual(Instant dataAtual);
}
