package com.Atividade.InovaEmpresa.Repositories;

import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
