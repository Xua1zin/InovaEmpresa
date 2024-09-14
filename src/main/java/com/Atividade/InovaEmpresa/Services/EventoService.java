package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

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

    public EventoEntity update(EventoEntity eventoEntity, Long id){
        try{
            eventoEntity.setId(id);
            return eventoRepository.save(eventoEntity);
        }catch(Exception e){
            System.out.println("Não foi possível atualizar o evento: " + e.getMessage());
            return new EventoEntity();
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
