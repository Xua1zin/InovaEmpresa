package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoPopularRepository;
import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoPopularService {
    @Autowired
    AvaliacaoPopularRepository avaliacaoPopularRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    IdeiaRepository ideiaRepository;
    @Autowired
    EventoRepository eventoRepository;

    public AvaliacaoPopularEntity votar(Long ideiaId, Long usuarioId){
        try{
            Instant atual = Instant.now();
            UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            IdeiaEntity ideiaEntity = ideiaRepository.findById(ideiaId)
                    .orElseThrow(() -> new IllegalArgumentException("Ideia não encontrada"));

            EventoEntity eventoAtual = eventoRepository.findEventoAtual(atual)
                    .orElseThrow(() -> new IllegalArgumentException("Nenhum evento ativo encontrado"));

            List<IdeiaEntity> topIdeias = ideiaRepository.findTop10ByJurados(eventoAtual.getId());

            boolean isTop10 = topIdeias.stream()
                    .anyMatch(ideia -> ideia.getId().equals(ideiaEntity.getId()));

            if (!isTop10) {
                throw new IllegalArgumentException("A ideia não está entre as top 10 ideias do evento.");
            }else {
                if (atual.equals(ideiaEntity.getEvento().getDataAvaliacaoJurado())) {
                    Optional<AvaliacaoPopularEntity> avaliacaoExistente =
                            avaliacaoPopularRepository.findByUsuarioIdAndEventoId(usuarioId, ideiaEntity.getEvento().getId());

                    if (avaliacaoExistente.isPresent()) {
                        throw new IllegalArgumentException("Usuário já votou neste evento");
                    }

                    AvaliacaoPopularEntity novaAvaliacao = new AvaliacaoPopularEntity();
                    novaAvaliacao.setUsuario(usuarioEntity);
                    novaAvaliacao.setIdeia(ideiaEntity);
                    novaAvaliacao.setEvento(ideiaEntity.getEvento());

                    return avaliacaoPopularRepository.save(novaAvaliacao);
                } else {
                    System.out.println("Fora da data de permitida para voto");
                    return null;
                }
            }
        }catch (Exception e){
            System.out.println("Erro ao votar");
            return new AvaliacaoPopularEntity();
        }
    }

    public AvaliacaoPopularEntity findById(Long id){
        try{
            return avaliacaoPopularRepository.findById(id).
                    orElseThrow(() ->{
                       System.out.println("Avaliação popular não encontrada com o ID: "+ id);
                       return new RuntimeException("Avaliação popular não encontrada");
                    });
        } catch (Exception e){
            System.out.println("Erro ao buscar avaliação popular: " + id);
            return new AvaliacaoPopularEntity();
        }
    }
}
