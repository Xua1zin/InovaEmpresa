package com.Atividade.InovaEmpresa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacaoPopular")
public class AvaliacaoPopularEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double nota;

    @ManyToOne
    @JoinColumn(name = "ideia_id")
    private IdeiaEntity ideia;

    @ManyToMany
    @JoinTable(
            name = "avaliacao_popular_usuario",
            joinColumns = @JoinColumn(name = "avaliacao_popular_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<UsuarioEntity> usuarios;
}
