package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoPopularRepository;
import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AvaliacaoPopularServiceTest {

    @SpyBean
    private AvaliacaoPopularService avaliacaoPopularService;

    @MockBean
    private AvaliacaoPopularRepository avaliacaoPopularRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private IdeiaRepository ideiaRepository;

    @MockBean
    private EventoRepository eventoRepository;

    @Test
    void testVotarSuccess() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;
        Instant atual = Instant.now();

        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoPopular(atual.minusSeconds(3600)); // Set to 1 hour ago
        ideia.setEvento(evento);
        ideia.setId(ideiaId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoPopular(any(Instant.class))).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(evento.getId())).thenReturn(List.of(ideia));
        when(avaliacaoPopularRepository.findByUsuarioIdAndEventoId(usuarioId, evento.getId())).thenReturn(Optional.empty());
        when(avaliacaoPopularRepository.save(any(AvaliacaoPopularEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AvaliacaoPopularEntity result = avaliacaoPopularService.votar(ideiaId, usuarioId);

        assertNotNull(result);
        assertEquals(usuario, result.getUsuario());
        assertEquals(ideia, result.getIdeia());
        assertEquals(evento, result.getEvento());
    }

    @Test
    void testVotarUsuarioNaoEncontrado() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        AvaliacaoPopularEntity result = avaliacaoPopularService.votar(ideiaId, usuarioId);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testVotarIdeiaNaoEncontrada() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(new UsuarioEntity()));
        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.empty());

        AvaliacaoPopularEntity result = avaliacaoPopularService.votar(ideiaId, usuarioId);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testVotarEventoNaoEncontrado() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(new UsuarioEntity()));
        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(new IdeiaEntity()));
        when(eventoRepository.findEventoPopular(any(Instant.class))).thenReturn(Optional.empty());

        AvaliacaoPopularEntity result = avaliacaoPopularService.votar(ideiaId, usuarioId);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testVotarIdeiaNaoTop10() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;
        Instant atual = Instant.now();

        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        ideia.setEvento(evento);
        ideia.setId(ideiaId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoPopular(any(Instant.class))).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(evento.getId())).thenReturn(List.of(new IdeiaEntity()));

        AvaliacaoPopularEntity result = avaliacaoPopularService.votar(ideiaId, usuarioId);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testVotarForaDataPermitida() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;
        Instant atual = Instant.now();

        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoPopular(atual.plusSeconds(3600));
        ideia.setEvento(evento);
        ideia.setId(ideiaId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoPopular(any(Instant.class))).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(evento.getId())).thenReturn(List.of(ideia));

        AvaliacaoPopularEntity result = avaliacaoPopularService.votar(ideiaId, usuarioId);

        assertNull(result);
    }

    @Test
    void testVotarUsuarioJaVotou() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;
        Instant atual = Instant.now();

        UsuarioEntity usuario = new UsuarioEntity();
        IdeiaEntity ideia = new IdeiaEntity();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoPopular(atual.minusSeconds(3600));
        ideia.setEvento(evento);
        ideia.setId(ideiaId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoPopular(any(Instant.class))).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(evento.getId())).thenReturn(List.of(ideia));
        when(avaliacaoPopularRepository.findByUsuarioIdAndEventoId(usuarioId, evento.getId())).thenReturn(Optional.of(new AvaliacaoPopularEntity()));

        AvaliacaoPopularEntity result = avaliacaoPopularService.votar(ideiaId, usuarioId);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testFindByIdSuccess() {
        Long id = 1L;
        AvaliacaoPopularEntity avaliacao = new AvaliacaoPopularEntity();
        avaliacao.setId(id);

        when(avaliacaoPopularRepository.findById(id)).thenReturn(Optional.of(avaliacao));

        AvaliacaoPopularEntity result = avaliacaoPopularService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 1L;

        when(avaliacaoPopularRepository.findById(id)).thenReturn(Optional.empty());

        AvaliacaoPopularEntity result = avaliacaoPopularService.findById(id);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }
}