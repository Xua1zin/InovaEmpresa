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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IdeiaServiceTest {

    @InjectMocks
    private IdeiaService ideiaService;

    @Mock
    private IdeiaRepository ideiaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EventoRepository eventoRepository;

    private EventoEntity eventoMock;
    private UsuarioEntity usuarioMock;
    private IdeiaEntity ideiaMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Mocking IdeiaEntity
        ideiaMock = new IdeiaEntity();
        ideiaMock.setUsuarios(new ArrayList<>());

        // Mocking UsuarioEntity
        usuarioMock = new UsuarioEntity();
        usuarioMock.setId(1L);
        usuarioMock.setFlIdeia(false);

        // Mocking EventoEntity
        eventoMock = new EventoEntity();
        eventoMock.setDataFim(Instant.now().plusSeconds(3600));
    }


    @Test
    void testAtribuirNota_UpdateExistingAvaliacao() {
        AvaliacaoJuradoEntity existingAvaliacao = new AvaliacaoJuradoEntity();
        existingAvaliacao.setUsuarios(List.of(usuarioMock));
        ideiaMock.setAvaliacaoJurado(List.of(existingAvaliacao));

        AvaliacaoJuradoEntity result = IdeiaService.atribuirNota(usuarioMock, ideiaMock, 8.5);

        assertNotNull(result);
        assertEquals(8.5, result.getNota());
    }

    @Test
    void testAddColaboradores_Success() {
        List<Long> usuarioIds = List.of(1L);

        when(ideiaRepository.findById(1L)).thenReturn(Optional.of(ideiaMock));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(ideiaRepository.save(any(IdeiaEntity.class))).thenReturn(ideiaMock);

        IdeiaEntity result = ideiaService.addColaboradores(1L, usuarioIds);

        assertNotNull(result);
        assertTrue(ideiaMock.getUsuarios().contains(usuarioMock));
        verify(ideiaRepository, times(1)).save(ideiaMock);
    }



    @Test
    void testSaveIdea_Success() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(eventoMock));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(ideiaRepository.save(any(IdeiaEntity.class))).thenReturn(ideiaMock);

        IdeiaEntity result = ideiaService.save(ideiaMock, 1L);

        assertNotNull(result);
        verify(ideiaRepository, times(1)).save(ideiaMock);
    }



    @Test
    void testFindAllIdeas_Success() {
        when(ideiaRepository.findAll()).thenReturn(List.of(ideiaMock));

        List<IdeiaEntity> result = ideiaService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(ideiaRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(ideiaRepository.findById(1L)).thenReturn(Optional.of(ideiaMock));

        IdeiaEntity result = ideiaService.findById(1L);

        assertNotNull(result);
        assertEquals(ideiaMock, result);
        verify(ideiaRepository, times(1)).findById(1L);
    }


}