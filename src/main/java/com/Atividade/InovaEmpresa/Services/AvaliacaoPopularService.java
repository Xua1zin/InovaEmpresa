package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoPopularRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoPopularService {
    @Autowired
    AvaliacaoPopularRepository avaliacaoPopularRepository;

    public AvaliacaoPopularEntity save(AvaliacaoPopularEntity avaliacaoPopularEntity){
        try{
            return avaliacaoPopularRepository.save(avaliacaoPopularEntity);
        }catch(Exception e){
            System.out.println("Não foi possível salvar a avaliação popular: "+ e.getMessage());
            return new AvaliacaoPopularEntity();
        }
    }

    public String delete(Long id){
        try{
            avaliacaoPopularRepository.deleteById(id);
            return "Avaliação popular deletedo com sucesso";
        }catch(Exception e){
            System.out.println("Não foi possível deletar a avaliação popular: " + e.getMessage());
            return "Não foi possível deletar a avaliação popular";
        }
    }

    public AvaliacaoPopularEntity update(AvaliacaoPopularEntity avaliacaoPopularEntity, Long id){
        try{
            avaliacaoPopularEntity.setId(id);
            return avaliacaoPopularRepository.save(avaliacaoPopularEntity);
        }catch(Exception e){
            System.out.println("Não foi possível atualizar a avaliação popular: " + e.getMessage());
            return new AvaliacaoPopularEntity();
        }
    }

    public List<AvaliacaoPopularEntity> findAll(){
        try{
            return avaliacaoPopularRepository.findAll();
        }catch(Exception e){
            System.out.println("Erro ao retornar lista de avaliação popular: " + e.getMessage());
            return List.of();
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
