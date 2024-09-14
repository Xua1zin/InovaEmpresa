package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeiaService {
    @Autowired
    IdeiaRepository ideiaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public IdeiaEntity save(IdeiaEntity ideiaEntity, Long id){
        try{
            UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));
            if(usuarioEntity.isFlIdeia()){
                ideiaEntity.getUsuarios().add(usuarioEntity);
                usuarioEntity.setFlIdeia(true);

                usuarioRepository.save(usuarioEntity);

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
