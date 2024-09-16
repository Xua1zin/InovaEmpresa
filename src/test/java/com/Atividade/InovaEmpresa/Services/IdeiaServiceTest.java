package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.AvaliacaoJuradoRepository;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class IdeiaServiceTest {

    @InjectMocks
    private IdeiaService ideiaService;

    @Mock
    private IdeiaRepository ideiaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EventoRepository eventoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResultadoSuccess() {
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setDataAvaliacaoPopular(Instant.now().minusSeconds(3600));
        when(eventoRepository.findById(any(Long.class))).thenReturn(Optional.of(eventoEntity));
        when(ideiaRepository.findTopIdeasByEvento(any(Long.class))).thenReturn(new ArrayList<>());

        List<IdeiaEntity> ideias = ideiaService.resultado(1L);
        assertNotNull(ideias);
        assertTrue(ideias.isEmpty());
    }

    @Test
    void testResultadoFail() {
        when(eventoRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        List<IdeiaEntity> ideias = ideiaService.resultado(1L);
        assertNotNull(ideias);
        assertTrue(ideias.isEmpty());
    }

    @Test
    void testAtribuirNotaSuccess() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        IdeiaEntity ideiaEntity = new IdeiaEntity();
        AvaliacaoJuradoEntity avaliacaoJuradoEntity = ideiaService.atribuirNota(usuarioEntity, ideiaEntity, 8.0);
        assertNotNull(avaliacaoJuradoEntity);
        assertEquals(8.0, avaliacaoJuradoEntity.getNota());
    }

    @Test
    void testAtribuirNotaFail() {
        IdeiaEntity ideiaEntity = mock(IdeiaEntity.class);
        UsuarioEntity usuarioEntity = mock(UsuarioEntity.class);
        doThrow(new RuntimeException("Erro")).when(ideiaEntity).getAvaliacaoJurado();
        AvaliacaoJuradoEntity avaliacaoJuradoEntity = ideiaService.atribuirNota(usuarioEntity, ideiaEntity, 8.0);
        assertNull(avaliacaoJuradoEntity);
    }

    @Test
    void testAddColaboradoresSuccess() {
        IdeiaEntity ideiaEntity = new IdeiaEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setFlIdeia(true);
        List<Long> usuarioIds = List.of(1L);
        when(ideiaRepository.findById(any(Long.class))).thenReturn(Optional.of(ideiaEntity));
        when(usuarioRepository.findById(any(Long.class))).thenReturn(Optional.of(usuarioEntity));
        when(ideiaRepository.save(any(IdeiaEntity.class))).thenReturn(ideiaEntity);

        IdeiaEntity result = ideiaService.addColaboradores(1L, usuarioIds);
        assertNotNull(result);
    }

    @Test
    void testAddColaboradoresFail() {
        List<Long> usuarioIds = List.of(1L);
        when(ideiaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        IdeiaEntity result = ideiaService.addColaboradores(1L, usuarioIds);
        assertNotNull(result);
    }

    @Test
    void testSaveSuccess() {
        IdeiaEntity ideiaEntity = new IdeiaEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setDataFim(Instant.now().minusSeconds(3600));
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(eventoEntity));
        when(usuarioRepository.findById(any(Long.class))).thenReturn(Optional.of(usuarioEntity));
        when(ideiaRepository.save(any(IdeiaEntity.class))).thenReturn(ideiaEntity);

        IdeiaEntity result = ideiaService.save(ideiaEntity, 1L);
        assertNotNull(result);
    }

    @Test
    void testSaveFail() {
        IdeiaEntity ideiaEntity = new IdeiaEntity();
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.empty());

        IdeiaEntity result = ideiaService.save(ideiaEntity, 1L);
        assertNull(result);
    }

    @Test
    void testFindAllSuccess() {
        when(ideiaRepository.findAll()).thenReturn(new ArrayList<>());

        List<IdeiaEntity> ideias = ideiaService.findAll();
        assertNotNull(ideias);
        assertTrue(ideias.isEmpty());
    }

    @Test
    void testFindAllFail() {
        when(ideiaRepository.findAll()).thenThrow(new RuntimeException("Erro"));

        List<IdeiaEntity> ideias = ideiaService.findAll();
        assertNotNull(ideias);
        assertTrue(ideias.isEmpty());
    }

    @Test
    void testFindByIdSuccess() {
        IdeiaEntity ideiaEntity = new IdeiaEntity();
        when(ideiaRepository.findById(any(Long.class))).thenReturn(Optional.of(ideiaEntity));

        IdeiaEntity result = ideiaService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindByIdFail() {
        when(ideiaRepository.findById(any(Long.class))).thenThrow(new RuntimeException("Erro"));

        IdeiaEntity result = ideiaService.findById(1L);
        assertNotNull(result);
    }
}
