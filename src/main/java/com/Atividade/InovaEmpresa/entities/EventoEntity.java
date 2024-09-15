package com.Atividade.InovaEmpresa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento")
public class EventoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private Instant dataInicio;

    @NotNull
    private Instant dataFim;

    @NotNull
    private Instant dataAvaliacaoJurado;

    @NotNull
    private Instant dataAvaliacaoPopular;

    @OneToMany(mappedBy = "evento")
    private List<IdeiaEntity> ideias;

    @ManyToMany(mappedBy = "eventos")
    private List<UsuarioEntity> usuarios;
}
