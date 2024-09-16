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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventoServiceTest {

    @InjectMocks
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private IdeiaRepository ideiaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDistribuicaoIdeiasParaJurados_Success() {
        // Setup
        Instant atual = Instant.now();
        EventoEntity eventoAtual = new EventoEntity();
        eventoAtual.setDataAvaliacaoJurado(atual);

        UsuarioEntity jurado1 = new UsuarioEntity();
        jurado1.setRole(UsuarioRole.JURADO);
        UsuarioEntity jurado2 = new UsuarioEntity();
        jurado2.setRole(UsuarioRole.JURADO);
        eventoAtual.setUsuarios(Arrays.asList(jurado1, jurado2));

        IdeiaEntity ideia1 = new IdeiaEntity();
        IdeiaEntity ideia2 = new IdeiaEntity();
        eventoAtual.setIdeias(Arrays.asList(ideia1, ideia2));

        when(eventoRepository.findEventoAtual(atual)).thenReturn(Optional.of(eventoAtual));

        EventoEntity eventoSalvo = new EventoEntity();
        when(eventoRepository.save(eventoAtual)).thenReturn(eventoSalvo);
        when(ideiaRepository.saveAll(anyList())).thenReturn(Arrays.asList(ideia1, ideia2));

        EventoEntity resultado = eventoService.distribuicaoIdeiasParaJurados();
        assertNotNull(resultado);
        verify(ideiaRepository).saveAll(anyList());
        verify(eventoRepository).save(eventoAtual);
    }

    @Test
    void testDistribuicaoIdeiasParaJurados_Fail_NoEvent() {
        Instant atual = Instant.now();
        when(eventoRepository.findEventoAtual(atual)).thenReturn(Optional.empty());
        EventoEntity resultado = eventoService.distribuicaoIdeiasParaJurados();
        assertNull(resultado);
        verify(ideiaRepository, never()).saveAll(anyList());
    }

    @Test
    void testDistribuicaoIdeiasParaJurados_Fail_NotEnoughJurados() {
        Instant atual = Instant.now();
        EventoEntity eventoAtual = new EventoEntity();
        eventoAtual.setDataAvaliacaoJurado(atual);

        UsuarioEntity jurado = new UsuarioEntity();
        jurado.setRole(UsuarioRole.JURADO);
        eventoAtual.setUsuarios(Collections.singletonList(jurado));

        when(eventoRepository.findEventoAtual(atual)).thenReturn(Optional.of(eventoAtual));
        assertThrows(IllegalArgumentException.class, () -> eventoService.distribuicaoIdeiasParaJurados());
    }

    @Test
    void testSave_Success() {
        // Setup
        EventoEntity eventoEntity = new EventoEntity();
        UsuarioEntity admin = new UsuarioEntity();
        admin.setRole(UsuarioRole.ADMIN);
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(admin));
        when(eventoRepository.save(eventoEntity)).thenReturn(eventoEntity);

        EventoEntity resultado = eventoService.save(eventoEntity, 1L);

        assertEquals(eventoEntity, resultado);
    }

    @Test
    void testSave_Fail_NotAdmin() {
        // Setup
        EventoEntity eventoEntity = new EventoEntity();
        UsuarioEntity user = new UsuarioEntity();
        user.setRole(UsuarioRole.COLABORADOR);
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(SecurityException.class, () -> eventoService.save(eventoEntity, 1L));
    }

    @Test
    void testDelete_Success() {
        doNothing().when(eventoRepository).deleteById(anyLong());
        String resultado = eventoService.delete(1L);
        assertEquals("Evento deletedo com sucesso", resultado);
    }

    @Test
    void testDelete_Fail() {
        doThrow(new RuntimeException("Error")).when(eventoRepository).deleteById(anyLong());
        String resultado = eventoService.delete(1L);
        assertEquals("Não foi possível deletar o evento", resultado);
    }

    @Test
    void testFindAll_Success() {
        EventoEntity evento = new EventoEntity();
        when(eventoRepository.findAll()).thenReturn(Collections.singletonList(evento));
        List<EventoEntity> eventos = eventoService.findAll();
        assertNotNull(eventos);
        assertFalse(eventos.isEmpty());
    }

    @Test
    void testFindAll_Fail() {
        when(eventoRepository.findAll()).thenThrow(new RuntimeException("Error"));
        List<EventoEntity> eventos = eventoService.findAll();
        assertTrue(eventos.isEmpty());
    }

    @Test
    void testFindById_Success() {
        EventoEntity evento = new EventoEntity();
        when(eventoRepository.findById(anyLong())).thenReturn(Optional.of(evento));
        EventoEntity resultado = eventoService.findById(1L);
        assertEquals(evento, resultado);
    }

    @Test
    void testFindById_Fail() {
        when(eventoRepository.findById(anyLong())).thenThrow(new RuntimeException("Error"));
        EventoEntity resultado = eventoService.findById(1L);
        assertNotNull(resultado);
    }
}
