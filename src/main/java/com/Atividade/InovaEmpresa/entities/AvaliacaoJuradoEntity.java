package com.Atividade.InovaEmpresa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "avaliacaoJurado")
public class AvaliacaoJuradoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double nota;

    @ManyToOne
    @JoinColumn(name = "ideia_id")
    @JsonIgnore
    private IdeiaEntity ideia;

    @ManyToMany
    @JoinTable(
            name = "avaliacao_jurado_usuario",
            joinColumns = @JoinColumn(name = "avaliacao_jurado_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnore
    private List<UsuarioEntity> usuarios;
}
