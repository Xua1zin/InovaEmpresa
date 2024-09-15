package com.Atividade.InovaEmpresa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "ideia")
public class IdeiaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String impacto;

    @NotNull
    private Double custo;

    @NotBlank
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventoEntity evento;

    @ManyToMany
    @JoinTable(
            name = "ideia_usuario",
            joinColumns = @JoinColumn(name = "ideia_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<UsuarioEntity> usuarios;

    @OneToMany(mappedBy = "ideia")
    private List<AvaliacaoJuradoEntity> avaliacaoJurado;

    @ManyToMany
    @JoinTable(
            name = "ideia_avaliacao_popular",
            joinColumns = @JoinColumn(name = "ideia_id"),
            inverseJoinColumns = @JoinColumn(name = "avaliacao_popular_id")
    )
    private List<AvaliacaoPopularEntity> avaliacaoPopular;
}
