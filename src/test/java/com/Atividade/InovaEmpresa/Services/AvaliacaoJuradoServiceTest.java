package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoJuradoRepository;
import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.Services.AvaliacaoJuradoService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class AvaliacaoJuradoServiceTest {

    @Autowired
    AvaliacaoJuradoService avaliacaoJuradoService;

    @MockBean
    AvaliacaoJuradoRepository avaliacaoJuradoRepository;

    @MockBean
    UsuarioRepository usuarioRepository;

    @MockBean
    IdeiaRepository ideiaRepository;

    @MockBean
    EventoRepository eventoRepository;

    @Test
    public void testVerNotaSuccess() {
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoJurado(Instant.now().minusSeconds(3600));
        Mockito.when(eventoRepository.findEventoJurado(any(Instant.class)))
                .thenReturn(Optional.of(evento));

        IdeiaEntity ideia = new IdeiaEntity();
        AvaliacaoJuradoEntity avaliacao1 = new AvaliacaoJuradoEntity();
        avaliacao1.setNota(7.0);
        AvaliacaoJuradoEntity avaliacao2 = new AvaliacaoJuradoEntity();
        avaliacao2.setNota(8.0);
        ideia.setAvaliacaoJurado(Arrays.asList(avaliacao1, avaliacao2));
        Mockito.when(ideiaRepository.findById(anyLong())).thenReturn(Optional.of(ideia));

        Double notaMedia = avaliacaoJuradoService.verNota(1L);
        assertEquals(7.5, notaMedia);
    }

    @Test
    public void testVerNotaEventNotFound() {
        Mockito.when(eventoRepository.findEventoJurado(any(Instant.class)))
                .thenReturn(Optional.empty());

        Double notaMedia = avaliacaoJuradoService.verNota(1L);
        assertEquals(0.0, notaMedia);
    }


    @Test
    public void testFindAll() {
        AvaliacaoJuradoEntity avaliacao1 = new AvaliacaoJuradoEntity();
        AvaliacaoJuradoEntity avaliacao2 = new AvaliacaoJuradoEntity();
        Mockito.when(avaliacaoJuradoRepository.findAll()).thenReturn(Arrays.asList(avaliacao1, avaliacao2));

        List<AvaliacaoJuradoEntity> result = avaliacaoJuradoService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByIdSuccess() {
        AvaliacaoJuradoEntity avaliacao = new AvaliacaoJuradoEntity();
        Mockito.when(avaliacaoJuradoRepository.findById(anyLong())).thenReturn(Optional.of(avaliacao));

        AvaliacaoJuradoEntity result = avaliacaoJuradoService.findById(1L);
        assertNotNull(result);
    }


}