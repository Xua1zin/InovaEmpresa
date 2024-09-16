package com.Atividade.InovaEmpresa.Repositories;

import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IdeiaRepository extends JpaRepository<IdeiaEntity, Long> {
    @Query(value = "SELECT i.* FROM ideia i " +
            "JOIN avaliacao_popular ap ON i.id = ap.ideia_id " +
            "WHERE i.evento_id = ?1 " +
            "GROUP BY i.id " +
            "ORDER BY AVG(ap.nota) DESC " +
            "LIMIT 10", nativeQuery = true)
    List<IdeiaEntity> findTop10ByJurados(@Param("eventoId") Long eventoId);

    @Query(value = "SELECT i.* FROM ideia i " +
            "JOIN avaliacao_popular ap ON i.id = ap.ideia_id " +
            "WHERE ap.evento_id = ?1 " +
            "GROUP BY i.id " +
            "ORDER BY AVG(ap.nota) DESC", nativeQuery = true)
    List<IdeiaEntity> findTopIdeasByEvento(@Param("eventoId") Long eventoId);

}
