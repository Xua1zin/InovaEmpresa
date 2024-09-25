package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventoServiceTest {

    @MockBean
    private EventoRepository eventoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private IdeiaRepository ideiaRepository;

    @Autowired
    private EventoService eventoService;

    private EventoEntity eventoEntity;
    private UsuarioEntity usuarioEntity;
    private IdeiaEntity ideiaEntity;

    @BeforeEach
    void setUp() {
        eventoEntity = new EventoEntity();
        eventoEntity.setId(1L);
        eventoEntity.setDataAvaliacaoJurado(Instant.now().minusSeconds(3600));
        eventoEntity.setDataAvaliacaoPopular(Instant.now().plusSeconds(3600));

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setRole(UsuarioRole.JURADO);

        ideiaEntity = new IdeiaEntity();
        ideiaEntity.setId(1L);
    }


    @Test
    void distribuicaoIdeiasParaJurados_NoEventFound() {
        when(eventoRepository.findEventoJurado(any(Instant.class))).thenReturn(Optional.empty());

        EventoEntity result = eventoService.distribuicaoIdeiasParaJurados();

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void distribuicaoIdeiasParaJurados_OutsideEvaluationPeriod() {
        eventoEntity.setDataAvaliacaoJurado(Instant.now().plusSeconds(3600));
        when(eventoRepository.findEventoJurado(any(Instant.class))).thenReturn(Optional.of(eventoEntity));

        EventoEntity result = eventoService.distribuicaoIdeiasParaJurados();

        assertNull(result);
    }

    @Test
    void distribuicaoIdeiasParaJurados_InsufficientJurados() {
        List<IdeiaEntity> ideias = new ArrayList<>();
        ideias.add(ideiaEntity);
        eventoEntity.setIdeias(ideias);

        List<UsuarioEntity> usuarios = new ArrayList<>();
        usuarios.add(usuarioEntity);
        eventoEntity.setUsuarios(usuarios);

        when(eventoRepository.findEventoJurado(any(Instant.class))).thenReturn(Optional.of(eventoEntity));

        EventoEntity result = eventoService.distribuicaoIdeiasParaJurados();

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void distribuicaoIdeiasParaJurados_NoIdeas() {
        eventoEntity.setIdeias(new ArrayList<>());

        List<UsuarioEntity> usuarios = new ArrayList<>();
        usuarios.add(usuarioEntity);
        usuarios.add(new UsuarioEntity());
        eventoEntity.setUsuarios(usuarios);

        when(eventoRepository.findEventoJurado(any(Instant.class))).thenReturn(Optional.of(eventoEntity));

        EventoEntity result = eventoService.distribuicaoIdeiasParaJurados();

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void addUsuarioEvento_Success() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(eventoEntity));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuarioEntity));
        when(eventoRepository.save(any(EventoEntity.class))).thenReturn(eventoEntity);

        EventoEntity result = eventoService.addUsuarioEvento(1L);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
        verify(eventoRepository, times(1)).save(any(EventoEntity.class));
    }

    @Test
    void addUsuarioEvento_NoEventFound() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.empty());

        EventoEntity result = eventoService.addUsuarioEvento(1L);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void addUsuarioEvento_UserNotFound() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(eventoEntity));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        EventoEntity result = eventoService.addUsuarioEvento(1L);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void save_AdminUser() {
        UsuarioEntity adminUser = new UsuarioEntity();
        adminUser.setRole(UsuarioRole.ADMIN);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(adminUser));
        when(eventoRepository.save(any(EventoEntity.class))).thenReturn(eventoEntity);

        EventoEntity result = eventoService.save(eventoEntity, 1L);

        assertNotNull(result);
        verify(eventoRepository, times(1)).save(any(EventoEntity.class));
    }

    @Test
    void save_NonAdminUser() {
        UsuarioEntity nonAdminUser = new UsuarioEntity();
        nonAdminUser.setRole(UsuarioRole.JURADO);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(nonAdminUser));

        EventoEntity result = eventoService.save(eventoEntity, 1L);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void save_UserNotFound() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        EventoEntity result = eventoService.save(eventoEntity, 1L);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void delete_Success() {
        String result = eventoService.delete(1L);

        assertEquals("Evento deletedo com sucesso", result);
        verify(eventoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void delete_Failure() {
        doThrow(new RuntimeException("Delete failed")).when(eventoRepository).deleteById(anyLong());

        String result = eventoService.delete(1L);

        assertEquals("Não foi possível deletar o evento", result);
    }

    @Test
    void findAll_Success() {
        List<EventoEntity> eventos = new ArrayList<>();
        eventos.add(eventoEntity);
        when(eventoRepository.findAll()).thenReturn(eventos);

        List<EventoEntity> result = eventoService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findAll_EmptyList() {
        when(eventoRepository.findAll()).thenReturn(new ArrayList<>());

        List<EventoEntity> result = eventoService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_Exception() {
        when(eventoRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        List<EventoEntity> result = eventoService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_Success() {
        when(eventoRepository.findById(anyLong())).thenReturn(Optional.of(eventoEntity));

        EventoEntity result = eventoService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_NotFound() {
        when(eventoRepository.findById(anyLong())).thenReturn(Optional.empty());

        EventoEntity result = eventoService.findById(1L);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }
}