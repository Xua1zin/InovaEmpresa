package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoJuradoRepository;
import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class IdeiaService {
    @Autowired
    IdeiaRepository ideiaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    EventoRepository eventoRepository;

    public List<IdeiaEntity> resultado(Long eventoId) {
        try{
            EventoEntity eventoEntity = eventoRepository.findById(eventoId)
                    .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
            Instant atual = Instant.now();
            if(atual.isAfter(eventoEntity.getDataAvaliacaoPopular()) || atual.equals(eventoEntity.getDataAvaliacaoPopular())){
                return ideiaRepository.findTopIdeasByEvento(eventoId);
            }else{
                System.out.println("Fora da data da votação");
                return new ArrayList<>();
            }
        } catch(Exception e){
            System.out.println("Erro ao encontrar resultados");
            return new ArrayList<>();
        }
    }

    public static AvaliacaoJuradoEntity atribuirNota(UsuarioEntity usuarioEntity, IdeiaEntity ideiaEntity, Double nota) {
        try {
            AvaliacaoJuradoEntity avaliacaoExistente = ideiaEntity.getAvaliacaoJurado().stream()
                    .filter(avaliacao -> avaliacao.getUsuarios().contains(usuarioEntity))
                    .findFirst()
                    .orElse(null);

            if (avaliacaoExistente != null) {
                avaliacaoExistente.setNota(nota);
                return avaliacaoExistente;
            } else {
                AvaliacaoJuradoEntity novaAvaliacao = new AvaliacaoJuradoEntity();
                novaAvaliacao.setNota(nota);
                novaAvaliacao.setIdeia(ideiaEntity);
                novaAvaliacao.setUsuarios(List.of(usuarioEntity));

                ideiaEntity.getAvaliacaoJurado().add(novaAvaliacao);

                return novaAvaliacao;
            }
        } catch (Exception e) {
            System.out.println("Não foi possível atribuir nota: " + e.getMessage());
            return null;
        }
    }


    public IdeiaEntity addColaboradores(Long ideiaId, List<Long> usuarioId){
        try{
            IdeiaEntity ideiaEntity = ideiaRepository.findById(ideiaId)
                    .orElseThrow(() -> new IllegalArgumentException("ideia não encontrada"));
            for(Long id : usuarioId){
                UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario com ID " + id + " não econtrado"));
                if(!ideiaEntity.getUsuarios().contains(usuarioEntity) && usuarioEntity.isFlIdeia()){
                    ideiaEntity.getUsuarios().add(usuarioEntity);
                }
            }
            return ideiaRepository.save(ideiaEntity);
        } catch (Exception e) {
            System.out.println("Não foi possível adicionar colaboradores: " + e.getMessage());
            return new IdeiaEntity();
        }
    }

    public IdeiaEntity save(IdeiaEntity ideiaEntity, Long usuarioId){
        try{
            Instant atual = Instant.now();
            EventoEntity eventoAtual = eventoRepository.findEventoAtual(atual)
                    .orElseThrow(() -> new IllegalArgumentException("Nenhum evento ativo encontrado"));
            if(atual.equals(eventoAtual.getDataFim()) || atual.isAfter(eventoAtual.getDataFim())) {
                UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioId)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));

                if (usuarioEntity.isFlIdeia()) {

                    usuarioEntity.setFlIdeia(true);
                    usuarioRepository.save(usuarioEntity);

                    ideiaEntity.setEvento(eventoAtual);

                    ideiaEntity.getUsuarios().add(usuarioEntity);

                    return ideiaRepository.save(ideiaEntity);
                } else {
                    throw new SecurityException("Esse usuário já tem uma ideia vinculada");
                }
            }else{
                System.out.println("Criação de ideias fora da data limite do evento não é permitido");
                return null;
            }
        }catch(Exception e){
            System.out.println("Não foi possível salvar a ideia: "+ e.getMessage());
            return new IdeiaEntity();
        }
    }

    public List<IdeiaEntity> findAll(){
        try{
            return ideiaRepository.findAll();
        }catch(Exception e){
            System.out.println("Erro ao retornar lista de ideia: " + e.getMessage());
            return List.of();
        }
    }

    public IdeiaEntity findById(Long id){
        try{
            return ideiaRepository.findById(id).
                    orElseThrow(() ->{
                       System.out.println("Ideia não encontrada com o ID: "+ id);
                       return new RuntimeException("Ideia não encontrada");
                    });
        } catch (Exception e){
            System.out.println("Erro ao buscar ideia: " + id);
            return new IdeiaEntity();
        }
    }
}
