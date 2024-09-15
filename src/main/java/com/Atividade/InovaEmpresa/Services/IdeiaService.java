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
    @Autowired
    AvaliacaoJuradoRepository avaliacaoJuradoRepository;

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

            UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));

            if(usuarioEntity.isFlIdeia()){
                EventoEntity eventoAtual = eventoRepository.findEventoAtual(atual)
                        .orElseThrow(() -> new IllegalArgumentException("Nenhum evento ativo encontrado"));

                usuarioEntity.setFlIdeia(true);
                usuarioRepository.save(usuarioEntity);

                ideiaEntity.setEvento(eventoAtual);

                ideiaEntity.getUsuarios().add(usuarioEntity);

                return ideiaRepository.save(ideiaEntity);
            }else {
                throw new SecurityException("Esse usuário já tem uma ideia vinculada");
            }
        }catch(Exception e){
            System.out.println("Não foi possível salvar a ideia: "+ e.getMessage());
            return new IdeiaEntity();
        }
    }

    public String delete(Long id){
        try{
            ideiaRepository.deleteById(id);
            return "Ideia deleteda com sucesso";
        }catch(Exception e){
            System.out.println("Não foi possível deletar a ideia: " + e.getMessage());
            return "Não foi possível deletar a ideia";
        }
    }

    public IdeiaEntity update(IdeiaEntity ideiaEntity, Long id){
        try{
            ideiaEntity.setId(id);
            return ideiaRepository.save(ideiaEntity);
        }catch(Exception e){
            System.out.println("Não foi possível atualizar a ideia: " + e.getMessage());
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
