package com.Atividade.InovaEmpresa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email inválido")
    private String email;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    @NotNull
    private boolean flIdeia;

    @ManyToMany(mappedBy = "usuarios")
    private List<IdeiaEntity> ideias;

    @ManyToMany
    @JoinTable(
            name = "usuario_evento",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private List<EventoEntity> eventos;

    @ManyToMany(mappedBy = "usuarios")
    private List<AvaliacaoJuradoEntity> avaliacaoJurado;

    @ManyToMany(mappedBy = "usuarios")
    private List<AvaliacaoPopularEntity> avaliacaoPopular;
}
