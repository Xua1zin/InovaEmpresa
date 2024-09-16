package com.Atividade.InovaEmpresa.Repositories;

import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdeiaRepository extends JpaRepository<IdeiaEntity, Long> {
    @Query(value = "SELECT * FROM ideia i JOIN i.avaliacaoPopular ap " +
            "WHERE i.evento.id = ? " +
            "GROUP BY i " +
            "ORDER BY AVG(ap.nota) DESC", nativeQuery = true)
    List<IdeiaEntity> findTop10ByEvento(Long eventoId);
}
