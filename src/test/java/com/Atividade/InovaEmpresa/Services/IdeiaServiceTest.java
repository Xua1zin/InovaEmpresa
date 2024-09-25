package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.IdeiaRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class IdeiaServiceTest {

    @Autowired
    private IdeiaService ideiaService;

    @MockBean
    private IdeiaRepository ideiaRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private EventoRepository eventoRepository;

    private EventoEntity eventoMock;
    private UsuarioEntity usuarioMock;
    private IdeiaEntity ideiaMock;

    @BeforeEach
    public void setup() {
        eventoMock = new EventoEntity();
        eventoMock.setId(1L);
        eventoMock.setDataFim(Instant.now().plusSeconds(3600));
        eventoMock.setDataAvaliacaoPopular(Instant.now().minusSeconds(3600));

        usuarioMock = new UsuarioEntity();
        usuarioMock.setId(1L);
        usuarioMock.setFlIdeia(false);

        ideiaMock = new IdeiaEntity();
        ideiaMock.setId(1L);
        ideiaMock.setUsuarios(new ArrayList<>());
        ideiaMock.setAvaliacaoJurado(new ArrayList<>());
    }

    @Test
    void testResultado_Success() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(eventoMock));
        when(ideiaRepository.findTopIdeasByEvento(1L)).thenReturn(List.of(ideiaMock));

        List<IdeiaEntity> result = ideiaService.resultado(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(ideiaRepository, times(1)).findTopIdeasByEvento(1L);
    }

    @Test
    void testResultado_BeforeAvaliacaoPopular() {
        eventoMock.setDataAvaliacaoPopular(Instant.now().plusSeconds(3600));
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(eventoMock));

        List<IdeiaEntity> result = ideiaService.resultado(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testResultado_EventoNotFound() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        List<IdeiaEntity> result = ideiaService.resultado(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testAtribuirNota_UpdateExisting() {
        AvaliacaoJuradoEntity existingAvaliacao = new AvaliacaoJuradoEntity();
        existingAvaliacao.setUsuarios(List.of(usuarioMock));
        ideiaMock.setAvaliacaoJurado(List.of(existingAvaliacao));

        AvaliacaoJuradoEntity result = IdeiaService.atribuirNota(usuarioMock, ideiaMock, 8.5);

        assertNotNull(result);
        assertEquals(8.5, result.getNota());
    }

    @Test
    void testAtribuirNota_CreateNew() {
        AvaliacaoJuradoEntity result = IdeiaService.atribuirNota(usuarioMock, ideiaMock, 9.0);

        assertNotNull(result);
        assertEquals(9.0, result.getNota());
        assertTrue(ideiaMock.getAvaliacaoJurado().contains(result));
    }

    @Test
    void testAddColaboradores_Success() {
        when(ideiaRepository.findById(1L)).thenReturn(Optional.of(ideiaMock));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(ideiaRepository.save(any(IdeiaEntity.class))).thenReturn(ideiaMock);

        IdeiaEntity result = ideiaService.addColaboradores(1L, List.of(1L));

        assertNotNull(result);
        assertTrue(result.getUsuarios().contains(usuarioMock));
        verify(usuarioRepository, times(1)).save(usuarioMock);
    }



    @Test
    void testSave_Success() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(eventoMock));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(ideiaRepository.save(any(IdeiaEntity.class))).thenReturn(ideiaMock);

        IdeiaEntity result = ideiaService.save(ideiaMock, 1L);

        assertNotNull(result);
        assertTrue(result.getUsuarios().contains(usuarioMock));
        verify(usuarioRepository, times(1)).save(usuarioMock);
    }



    @Test
    void testSave_ForaDataLimite() {
        eventoMock.setDataFim(Instant.now().minusSeconds(3600));
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(eventoMock));

        IdeiaEntity result = ideiaService.save(ideiaMock, 1L);

        assertNull(result);
    }

    @Test
    void testFindAll_Success() {
        when(ideiaRepository.findAll()).thenReturn(List.of(ideiaMock));

        List<IdeiaEntity> result = ideiaService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindAll_Exception() {
        when(ideiaRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        List<IdeiaEntity> result = ideiaService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_Success() {
        when(ideiaRepository.findById(1L)).thenReturn(Optional.of(ideiaMock));

        IdeiaEntity result = ideiaService.findById(1L);

        assertNotNull(result);
        assertEquals(ideiaMock.getId(), result.getId());
    }

    @Test
    void testFindById_NotFound() {
        when(ideiaRepository.findById(1L)).thenReturn(Optional.empty());

        IdeiaEntity result = ideiaService.findById(1L);

        assertNotNull(result);
        assertNull(result.getId());
    }
}