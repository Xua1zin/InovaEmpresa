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
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;

    import java.time.Instant;
    import java.util.ArrayList;
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
        EventoRepository eventoRepository;

        public Double verNota(Long id){
            try{
                Instant atual = Instant.now();

                EventoEntity eventoAtual = eventoRepository.findEventoJurado(atual)
                        .orElseThrow(() -> new IllegalArgumentException("Nenhum evento ativo encontrado"));

                if(atual.isAfter(eventoAtual.getDataAvaliacaoJurado()) || atual.equals(eventoAtual.getDataAvaliacaoJurado())){
                    IdeiaEntity ideiaEntity = ideiaRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("ideia não encontrada"));
                    List<AvaliacaoJuradoEntity> avaliacaoJuradoList =  ideiaEntity.getAvaliacaoJurado();

                    if (avaliacaoJuradoList.isEmpty()) {
                        System.out.println("Nenhuma avaliação encontrada para a ideia com ID: " + id);
                        return 0.0;
                    }

                    Double media = avaliacaoJuradoList.stream()
                            .mapToDouble(AvaliacaoJuradoEntity::getNota)
                            .average()
                            .orElse(0.0);

                    return media;
                }else {
                    System.out.println("Está fora da data de divulgação");
                    return 0.0;
                }
            }catch (Exception e){
                System.out.println("Não foi possível ver a nota"+e.getMessage());
                return 0.0;
            }
        }

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

                    if (usuarioEntity.getRole().toString().equals("JURADO")) {
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
