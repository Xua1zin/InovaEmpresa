package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoJuradoRepository;
import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AvaliacaoJuradoServiceTest {

    @Autowired
    private AvaliacaoJuradoService avaliacaoJuradoService;

    @MockBean
    private AvaliacaoJuradoRepository avaliacaoJuradoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private IdeiaRepository ideiaRepository;

    @MockBean
    private EventoRepository eventoRepository;

    @Test
    void verNotaComSucesso() {
        EventoEntity evento = mock(EventoEntity.class);
        IdeiaEntity ideia = mock(IdeiaEntity.class);
        AvaliacaoJuradoEntity avaliacao1 = mock(AvaliacaoJuradoEntity.class);
        AvaliacaoJuradoEntity avaliacao2 = mock(AvaliacaoJuradoEntity.class);

        when(evento.getDataAvaliacaoJurado()).thenReturn(Instant.now());
        when(eventoRepository.findEventoJurado(any())).thenReturn(Optional.of(evento));
        when(ideia.getAvaliacaoJurado()).thenReturn(List.of(avaliacao1, avaliacao2));
        when(avaliacao1.getNota()).thenReturn(8.0);
        when(avaliacao2.getNota()).thenReturn(9.0);
        when(ideiaRepository.findById(1L)).thenReturn(Optional.of(ideia));

        Double notaMedia = avaliacaoJuradoService.verNota(1L);

        assertNotNull(notaMedia);
        verify(ideiaRepository, times(1)).findById(1L);  // Verificando se o repositório foi chamado
    }

    @Test
    void verNotaSemAvaliacoes() {
        EventoEntity evento = mock(EventoEntity.class);
        IdeiaEntity ideia = mock(IdeiaEntity.class);

        when(evento.getDataAvaliacaoJurado()).thenReturn(Instant.now());
        when(eventoRepository.findEventoJurado(any())).thenReturn(Optional.of(evento));
        when(ideia.getAvaliacaoJurado()).thenReturn(new ArrayList<>());
        when(ideiaRepository.findById(1L)).thenReturn(Optional.of(ideia));

        Double notaMedia = avaliacaoJuradoService.verNota(1L);

        assertNotNull(notaMedia);
        verify(ideiaRepository, times(1)).findById(1L);
    }

    @Test
    void salvarAvaliacaoComSucesso() {
        UsuarioEntity usuario = mock(UsuarioEntity.class);
        IdeiaEntity ideia = mock(IdeiaEntity.class);
        AvaliacaoJuradoEntity avaliacao = mock(AvaliacaoJuradoEntity.class);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(1L)).thenReturn(Optional.of(ideia));
        when(usuario.getRole()).thenReturn(UsuarioRole.valueOf("JURADO"));
        when(ideia.getUsuarios()).thenReturn(List.of(usuario));
        when(avaliacaoJuradoRepository.save(any(AvaliacaoJuradoEntity.class))).thenReturn(avaliacao);

        AvaliacaoJuradoEntity resultado = avaliacaoJuradoService.save(1L, 1L, 8.0);

        assertNotNull(resultado);
        verify(avaliacaoJuradoRepository, times(1)).save(any(AvaliacaoJuradoEntity.class));
    }

    @Test
    void salvarAvaliacaoNotaForaDoIntervalo() {
        AvaliacaoJuradoEntity resultado = avaliacaoJuradoService.save(1L, 1L, 2.0);

        assertEquals(null, resultado);
    }


    @Test
    void buscarTodos() {
        List<AvaliacaoJuradoEntity> avaliacoes = List.of(new AvaliacaoJuradoEntity(), new AvaliacaoJuradoEntity());
        when(avaliacaoJuradoRepository.findAll()).thenReturn(avaliacoes);

        List<AvaliacaoJuradoEntity> resultado = avaliacaoJuradoService.findAll();

        assertNotNull(resultado);
        verify(avaliacaoJuradoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorIdComSucesso() {
        AvaliacaoJuradoEntity avaliacao = new AvaliacaoJuradoEntity();
        when(avaliacaoJuradoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        AvaliacaoJuradoEntity resultado = avaliacaoJuradoService.findById(1L);

        assertNotNull(resultado);
        verify(avaliacaoJuradoRepository, times(1)).findById(1L);
    }
}
