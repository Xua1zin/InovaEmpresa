package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioRole;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventoService {
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    IdeiaRepository ideiaRepository;

    public EventoEntity distribuicaoIdeiasParaJurados() {
        try{
            Instant atual = Instant.now();

            EventoEntity eventoAtual = eventoRepository.findEventoAtual(atual)
                    .orElseThrow(() -> new IllegalArgumentException("Nenhum evento atual encontrado"));

            if(atual.equals(eventoAtual.getDataAvaliacaoJurado())) {
                List<IdeiaEntity> ideias = eventoAtual.getIdeias();
                List<UsuarioEntity> jurados = eventoAtual.getUsuarios().stream()
                        .filter(usuarioEntity -> "JURADO".equals(usuarioEntity.getRole().name()))
                        .collect(Collectors.toList());

                if (jurados.size() < 2) {
                    throw new IllegalArgumentException("É necessário pelo menos 2 jurados para distribuir ideias");
                }
                if (ideias.isEmpty()) {
                    throw new IllegalArgumentException("Nenhuma ideia encontrada");
                }

                int quantidadeJuradosPorIdeias = ideias.size() / jurados.size();
                int restoDaDivisao = ideias.size() % jurados.size();
                quantidadeJuradosPorIdeias += restoDaDivisao;
                if (quantidadeJuradosPorIdeias <= 2) {
                    quantidadeJuradosPorIdeias = 2;
                }
                Collections.shuffle(jurados, new Random());

                for (IdeiaEntity ideia : ideias) {
                    List<UsuarioEntity> juradosParaIdeia = new ArrayList<>();
                    int numJurados = jurados.size();
                    for (int i = 0; i < quantidadeJuradosPorIdeias; i++) {
                        juradosParaIdeia.add(jurados.get((i + ideia.hashCode()) % numJurados));
                    }
                    ideia.setUsuarios(juradosParaIdeia);
                }

                ideiaRepository.saveAll(ideias);

                return eventoRepository.save(eventoAtual);
            }else{
                System.out.println("Fora da data de avaliação dos Jurados");
                return null;
            }
        }catch(Exception e){
            System.out.println("Erro ao distribuir ideias para jurados");
            return new EventoEntity();
        }
    }

    public EventoEntity save(EventoEntity eventoEntity, Long id){
        try{
            UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));
            if("ADMIN".equals(usuarioEntity.getRole())){
                return eventoRepository.save(eventoEntity);
            }else {
                throw new SecurityException("Apenas administradores podem criar eventos.");
            }
        }catch(Exception e){
            System.out.println("Não foi possível salvar o evento: "+ e.getMessage());
            return new EventoEntity();
        }
    }

    public String delete(Long id){
        try{
            eventoRepository.deleteById(id);
            return "Evento deletedo com sucesso";
        }catch(Exception e){
            System.out.println("Não foi possível deletar o evento: " + e.getMessage());
            return "Não foi possível deletar o evento";
        }
    }

    public List<EventoEntity> findAll(){
        try{
            return eventoRepository.findAll();
        }catch(Exception e){
            System.out.println("Erro ao retornar lista de evento: " + e.getMessage());
            return List.of();
        }
    }

    public EventoEntity findById(Long id){
        try{
            return eventoRepository.findById(id).
                    orElseThrow(() ->{
                       System.out.println("Evento não encontrado com o ID: "+ id);
                       return new RuntimeException("Evento não encontrado");
                    });
        } catch (Exception e){
            System.out.println("Erro ao buscar evento: " + id);
            return new EventoEntity();
        }
    }
}
