package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoJuradoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AvaliacaoJuradoService {
    @Autowired
    AvaliacaoJuradoRepository avaliacaoJuradoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    IdeiaRepository ideiaRepository;
    @Autowired
    IdeiaService ideiaService;

    public AvaliacaoJuradoEntity save(Long ideiaId, Long usuarioId, Double nota){
        if (nota < 3 || nota > 10) {
            System.out.println("Nota deve ser entre 3 e 10");
            return null;
        }else{
            try {
                IdeiaEntity ideiaEntity = ideiaRepository.findById(ideiaId)
                        .orElseThrow(() -> new IllegalArgumentException("Ideia não encontrada"));

                UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioId)
                        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

                if ("JURADO".equals(usuarioEntity.getRole())) {
                    if (ideiaEntity.getUsuarios().contains(usuarioEntity)) {
                        AvaliacaoJuradoEntity avaliacaoJuradoEntity = IdeiaService.atribuirNota(usuarioEntity, ideiaEntity, nota);

                        ideiaRepository.save(ideiaEntity);
                        return avaliacaoJuradoRepository.save(avaliacaoJuradoEntity);
                    } else {
                        throw new IllegalArgumentException("Este jurado não está atribuído a esta ideia");
                    }
                } else {
                    throw new IllegalArgumentException("Usuário não é jurado");
                }
            } catch (Exception e) {
                System.out.println("Erro ao dar nota para a ideia: " + e.getMessage());
                return null;
            }
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
