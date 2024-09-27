package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    //Alterado
    @Autowired
    EventoRepository eventoRepository;

    //Alterado
    public UsuarioEntity save(UsuarioEntity usuarioEntity){
        try{
            Instant atual = Instant.now();
            Optional<EventoEntity> eventoAtualOpt = eventoRepository.findEventoAtual(atual);
            if (eventoAtualOpt.isPresent()) {
                EventoEntity eventoAtual = eventoAtualOpt.get();
                if (usuarioEntity.getEventos() == null) {
                    usuarioEntity.setEventos(new ArrayList<>());
                }
                usuarioEntity.getEventos().add(eventoAtual);

                if (eventoAtual.getUsuarios() == null) {
                    eventoAtual.setUsuarios(new ArrayList<>());
                }
                eventoAtual.getUsuarios().add(usuarioEntity);
                eventoRepository.save(eventoAtual);
            }

            usuarioEntity.setFlIdeia(false);
            usuarioEntity.setRole(UsuarioRole.COLABORADOR);
            return usuarioRepository.save(usuarioEntity);
        } catch(Exception e){
            System.out.println("Não foi possível salvar o usuario: "+ e.getMessage());
            return new UsuarioEntity();
        }
    }

    public String delete(Long id){
        try{
            usuarioRepository.deleteById(id);
            return "Usuario deletedo com sucesso";
        }catch(Exception e){
            System.out.println("Não foi possível deletar o usuario: " + e.getMessage());
            return "Não foi possível deletar o usuario";
        }
    }

    //alterado
    public UsuarioEntity update(UsuarioEntity usuarioEntity, Long logadoId, Long id) {
        try {
            UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            UsuarioEntity usuarioLogado = usuarioRepository.findById(logadoId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário logado não encontrado"));

            if (usuarioLogado.getRole().toString().equals("ADMIN")) {
                usuarioExistente.setRole(usuarioEntity.getRole());
            }

            usuarioExistente.setNome(usuarioEntity.getNome());
            usuarioExistente.setEmail(usuarioEntity.getEmail());

            return usuarioRepository.save(usuarioExistente);
        } catch (Exception e) {
            System.out.println("Não foi possível atualizar o usuario: " + e.getMessage());
            return new UsuarioEntity();
        }
    }

    public List<UsuarioEntity> findAll(){
        try{
            return usuarioRepository.findAll();
        }catch(Exception e){
            System.out.println("Erro ao retornar lista de usuario: " + e.getMessage());
            return List.of();
        }
    }

    public UsuarioEntity findById(Long id){
        try{
            return usuarioRepository.findById(id).
                    orElseThrow(() ->{
                       System.out.println("Usuario não encontrado com o ID: "+ id);
                       return new RuntimeException("Usuario não encontrado");
                    });
        } catch (Exception e){
            System.out.println("Erro ao buscar Usuario: " + id);
            return new UsuarioEntity();
        }
    }

    //Alterado
    public List<UsuarioEntity> addJurados(List<Long> usuariosId, Long logadoId) {
        List<UsuarioEntity> updatedJurados = new ArrayList<>();
        try {
            UsuarioEntity logadoEntity = usuarioRepository.findById(logadoId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário logado não encontrado"));

            if (!logadoEntity.getRole().equals(UsuarioRole.ADMIN)) {
                throw new SecurityException("Acesso negado, usuário não é um admin");
            }

            for (Long id : usuariosId) {
                UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));

                usuarioEntity.setRole(UsuarioRole.JURADO);
                updatedJurados.add(usuarioEntity);
                usuarioRepository.save(usuarioEntity);
            }
            return updatedJurados;
        } catch (Exception e) {
            System.out.println("Não foi possível adicionar Jurados: " + e.getMessage());
            return new ArrayList<>();
        }
    }


}
