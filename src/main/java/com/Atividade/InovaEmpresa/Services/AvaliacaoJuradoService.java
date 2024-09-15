package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoJuradoRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoJuradoService {
    @Autowired
    AvaliacaoJuradoRepository avaliacaoJuradoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public AvaliacaoJuradoEntity save(AvaliacaoJuradoEntity avaliacaoJuradoEntity, Long id){
        try{
            UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));
            if("JURADO".equals(usuarioEntity.getRole())){
                return avaliacaoJuradoRepository.save(avaliacaoJuradoEntity);
            } else{
                throw new SecurityException("Usuário não é um jurado");
            }
        }catch(Exception e){
            System.out.println("Não foi possível salvar a avaliação jurado: "+ e.getMessage());
            return new AvaliacaoJuradoEntity();
        }
    }

    public String delete(Long id){
        try{
            avaliacaoJuradoRepository.deleteById(id);
            return "Avaliação jurado deletedo com sucesso";
        }catch(Exception e){
            System.out.println("Não foi possível deletar a avaliação jurado: " + e.getMessage());
            return "Não foi possível deletar a avaliação jurado";
        }
    }

    public AvaliacaoJuradoEntity update(AvaliacaoJuradoEntity avaliacaoJuradoEntity, Long id){
        try{
            avaliacaoJuradoEntity.setId(id);
            return avaliacaoJuradoRepository.save(avaliacaoJuradoEntity);
        }catch(Exception e){
            System.out.println("Não foi possível atualizar a avaliação jurado: " + e.getMessage());
            return new AvaliacaoJuradoEntity();
        }
    }

    public List<AvaliacaoJuradoEntity> findAll(){
        try{
            return avaliacaoJuradoRepository.findAll();
        }catch(Exception e){
            System.out.println("Erro ao retornar lista de avaliação jurado: " + e.getMessage());
            return List.of();
        }
    }

    public AvaliacaoJuradoEntity findById(Long id){
        try{
            return avaliacaoJuradoRepository.findById(id).
                    orElseThrow(() ->{
                       System.out.println("Avaliação jurado não encontrada com o ID: "+ id);
                       return new RuntimeException("Avaliação jurado não encontrada");
                    });
        } catch (Exception e){
            System.out.println("Erro ao buscar avaliação jurado: " + id);
            return new AvaliacaoJuradoEntity();
        }
    }
}
