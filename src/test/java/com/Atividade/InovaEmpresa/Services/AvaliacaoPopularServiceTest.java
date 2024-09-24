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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AvaliacaoPopularServiceTest {

    @MockBean
    AvaliacaoPopularRepository avaliacaoPopularRepository;

    @MockBean
    UsuarioRepository usuarioRepository;

    @MockBean
    IdeiaRepository ideiaRepository;

    @MockBean
    EventoRepository eventoRepository;

    @Autowired
    AvaliacaoPopularService service;

    @Test
    public void testVotar_WithActiveEventAndIdeia() {
        Long ideiaId = 1L;
        Long usuarioId = 1L;
        Instant now = Instant.now();
        EventoEntity evento = new EventoEntity();
        evento.setDataAvaliacaoPopular(now);
        IdeiaEntity ideia = new IdeiaEntity();
        ideia.setEvento(evento);
        UsuarioEntity usuario = new UsuarioEntity();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
        when(eventoRepository.findEventoPopular(now)).thenReturn(Optional.of(evento));
        when(ideiaRepository.findTop10ByJurados(evento.getId())).thenReturn(Arrays.asList(ideia));
        when(avaliacaoPopularRepository.findByUsuarioIdAndEventoId(usuarioId, evento.getId())).thenReturn(Optional.empty());
        when(avaliacaoPopularRepository.save(any(AvaliacaoPopularEntity.class))).thenReturn(new AvaliacaoPopularEntity());

        AvaliacaoPopularEntity resultado = service.votar(ideiaId, usuarioId);

        assertNotNull(resultado);
    }

////    @Test
////    public void testVotar_WithNonExistentUsuario() {
////        Long ideiaId = 1L;
////        Long usuarioId = 1L;
////        Instant now = Instant.now();
////        EventoEntity evento = new EventoEntity();
////        evento.setDataAvaliacaoPopular(now);
////        IdeiaEntity ideia = new IdeiaEntity();
////        ideia.setEvento(evento);
////
////        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());
////        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
////        when(eventoRepository.findEventoPopular(now)).thenReturn(Optional.of(evento));
////
////        AvaliacaoPopularEntity resultado = service.votar(ideiaId, usuarioId);
////
////        assertNull(resultado);
////    }
////
////    @Test
////    public void testVotar_WithNonExistentIdeia() {
////        Long ideiaId = 1L;
////        Long usuarioId = 1L;
////        Instant now = Instant.now();
////
////        UsuarioEntity usuario = new UsuarioEntity();
////        usuario.setId(usuarioId);
////
////        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
////        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.empty());
////        when(eventoRepository.findEventoPopular(now)).thenReturn(Optional.of(new EventoEntity()));
////
////        AvaliacaoPopularEntity resultado = service.votar(ideiaId, usuarioId);
////
////        assertNull(resultado);
////    }
////
////
////    @Test
////    public void testVotar_WithNonActiveEvent() {
////        Long ideiaId = 1L;
////        Long usuarioId = 1L;
////        Instant now = Instant.now();
////        EventoEntity evento = new EventoEntity();
////        evento.setDataAvaliacaoPopular(now.plusSeconds(3600));
////        IdeiaEntity ideia = new IdeiaEntity();
////        ideia.setEvento(evento);
////        UsuarioEntity usuario = new UsuarioEntity();
////
////        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
////        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
////        when(eventoRepository.findEventoPopular(now)).thenReturn(Optional.of(evento));
////
////        AvaliacaoPopularEntity resultado = service.votar(ideiaId, usuarioId);
////
////        assertNull(resultado);
////    }
////
////    @Test
////    public void testVotar_WithUserAlreadyVoted() {
////        Long ideiaId = 1L;
////        Long usuarioId = 1L;
////        Instant now = Instant.now();
////        EventoEntity evento = new EventoEntity();
////        evento.setDataAvaliacaoPopular(now);
////        IdeiaEntity ideia = new IdeiaEntity();
////        ideia.setEvento(evento);
////        UsuarioEntity usuario = new UsuarioEntity();
////        AvaliacaoPopularEntity existingAvaliacao = new AvaliacaoPopularEntity();
////
////        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
////        when(ideiaRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
////        when(eventoRepository.findEventoPopular(now)).thenReturn(Optional.of(evento));
////        when(ideiaRepository.findTop10ByJurados(evento.getId())).thenReturn(Arrays.asList(ideia));
////        when(avaliacaoPopularRepository.findByUsuarioIdAndEventoId(usuarioId, evento.getId())).thenReturn(Optional.of(existingAvaliacao));
////
////        AvaliacaoPopularEntity resultado = service.votar(ideiaId, usuarioId);
////
////        assertNull(resultado);
//    }

    @Test
    public void testFindById() {
        Long id = 1L;
        AvaliacaoPopularEntity avaliacao = new AvaliacaoPopularEntity();

        when(avaliacaoPopularRepository.findById(id)).thenReturn(Optional.of(avaliacao));

        AvaliacaoPopularEntity resultado = service.findById(id);

        assertEquals(avaliacao, resultado);
    }
}