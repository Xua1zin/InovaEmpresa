package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioEntity save(UsuarioEntity usuarioEntity){
        try{
            usuarioEntity.setFlIdeia(false);
            usuarioEntity.setRole(UsuarioRole.valueOf("COLABORADOR"));
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

    public UsuarioEntity update(UsuarioEntity usuarioEntity, Long logadoId, Long id) {
        try {
            UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            UsuarioEntity usuarioLogado = usuarioRepository.findById(logadoId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário logado não encontrado"));

            if ("ADMIN".equals(usuarioLogado.getRole()) && !usuarioEntity.getRole().equals(usuarioExistente.getRole())) {
                usuarioExistente.setRole(usuarioEntity.getRole());
            } else {
                throw new SecurityException("Apenas administradores podem alterar a role de outros usuários.");
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

    public List<UsuarioEntity> addJurados(List<Long> usuariosId, Long logadoId) {
        try {
            UsuarioEntity logadoEntity = usuarioRepository.findById(logadoId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário logado não encontrado"));
            if ("ADMIN".equals(logadoEntity.getRole())) {
                List<UsuarioEntity> usuarioEntitylist = new ArrayList<>();
                for (Long id : usuariosId) {
                    UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

                    usuarioEntity.setRole(UsuarioRole.valueOf("JURADO"));
                    usuarioEntitylist.add(usuarioEntity);
                }
                return usuarioEntitylist;
            } else {
                throw new SecurityException("Acesso negado, usuário não é um admin");
            }
        } catch (Exception e) {
            System.out.println("Não foi possível adicionar Jurados" + e.getMessage());
            return new ArrayList<>();
        }
    }

}
