package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoPopularRepository;
import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AvaliacaoPopularServiceTest {

    @InjectMocks
    private AvaliacaoPopularService avaliacaoPopularService;

    @Mock
    private AvaliacaoPopularRepository avaliacaoPopularRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private IdeiaRepository ideiaRepository;

    @Mock
    private EventoRepository eventoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVotar_Success() {
        Instant atual = Instant.now();
        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoJurado(atual);
        ideia.setEvento(evento);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(anyLong())).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoAtual(atual)).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(anyLong())).thenReturn(Collections.singletonList(ideia));
        when(avaliacaoPopularRepository.findByUsuarioIdAndEventoId(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(avaliacaoPopularRepository.save(any(AvaliacaoPopularEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AvaliacaoPopularEntity resultado = avaliacaoPopularService.votar(1L, 1L);

        assertNotNull(resultado);
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(ideia, resultado.getIdeia());
        assertEquals(evento, resultado.getEvento());
    }

    @Test
    void testVotar_Fail_IdeiaNotInTop10() {
        Instant atual = Instant.now();
        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoJurado(atual);
        ideia.setEvento(evento);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(anyLong())).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoAtual(atual)).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> avaliacaoPopularService.votar(1L, 1L));
    }

    @Test
    void testVotar_Fail_UsuarioJaVotou() {
        Instant atual = Instant.now();
        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoJurado(atual);
        ideia.setEvento(evento);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(anyLong())).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoAtual(atual)).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(anyLong())).thenReturn(Collections.singletonList(ideia));
        when(avaliacaoPopularRepository.findByUsuarioIdAndEventoId(anyLong(), anyLong())).thenReturn(Optional.of(new AvaliacaoPopularEntity()));

        assertThrows(IllegalArgumentException.class, () -> avaliacaoPopularService.votar(1L, 1L));
    }

    @Test
    void testVotar_Fail_ForaDaDataPermitida() {
        Instant atual = Instant.now().minusSeconds(1);
        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoJurado(atual);
        ideia.setEvento(evento);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(anyLong())).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoAtual(atual)).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(anyLong())).thenReturn(Collections.singletonList(ideia));

        assertNull(avaliacaoPopularService.votar(1L, 1L));
    }

    @Test
    void testFindById_Success() {
        AvaliacaoPopularEntity avaliacao = new AvaliacaoPopularEntity();
        when(avaliacaoPopularRepository.findById(anyLong())).thenReturn(Optional.of(avaliacao));

        AvaliacaoPopularEntity resultado = avaliacaoPopularService.findById(1L);

        assertEquals(avaliacao, resultado);
    }

    @Test
    void testFindById_Fail() {
        when(avaliacaoPopularRepository.findById(anyLong())).thenThrow(new RuntimeException("Error"));

        AvaliacaoPopularEntity resultado = avaliacaoPopularService.findById(1L);

        assertNotNull(resultado);
    }
}
