package com.Atividade.InovaEmpresa.Repositories;

import com.Atividade.InovaEmpresa.entities.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<EventoEntity, Long> {
}
