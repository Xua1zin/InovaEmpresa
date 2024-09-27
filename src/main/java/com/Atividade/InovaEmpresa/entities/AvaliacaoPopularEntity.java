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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacao_popular")
public class AvaliacaoPopularEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "ideia_id")
    private IdeiaEntity ideia;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonIgnore
    private EventoEntity evento;
}
