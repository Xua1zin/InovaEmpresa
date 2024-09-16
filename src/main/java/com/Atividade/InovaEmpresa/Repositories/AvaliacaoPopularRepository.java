package com.Atividade.InovaEmpresa.Repositories;

import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoPopularRepository extends JpaRepository<AvaliacaoPopularEntity, Long> {
    Optional<AvaliacaoPopularEntity> findByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
}
