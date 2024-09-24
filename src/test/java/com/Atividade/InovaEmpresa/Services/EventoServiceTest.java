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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @MockBean
    private EventoRepository eventoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private IdeiaRepository ideiaRepository;

    private EventoEntity eventoMock;
    private UsuarioEntity usuarioMock;
    private IdeiaEntity ideiaMock;

    @BeforeEach
    public void setup() {
        // Mocking EventoEntity
        eventoMock = new EventoEntity();
        eventoMock.setDataAvaliacaoJurado(Instant.now().minusSeconds(60));
        eventoMock.setDataAvaliacaoPopular(Instant.now().plusSeconds(3600));
        eventoMock.setIdeias(new ArrayList<>());
        eventoMock.setUsuarios(new ArrayList<>());

        // Mocking UsuarioEntity
        usuarioMock = new UsuarioEntity();
        usuarioMock.setId(1L);
        usuarioMock.setRole(UsuarioRole.ADMIN);
        eventoMock.getUsuarios().add(usuarioMock);

        // Mocking IdeiaEntity
        ideiaMock = new IdeiaEntity();
        eventoMock.getIdeias().add(ideiaMock);
    }

    @Test
    public void testAddUsuarioEvento_Success() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(eventoMock));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioMock);
        when(eventoRepository.save(any(EventoEntity.class))).thenReturn(eventoMock);

        EventoEntity result = eventoService.addUsuarioEvento(1L);

        assertNotNull(result);
        assertEquals(eventoMock, result);
        verify(usuarioRepository, times(1)).save(usuarioMock);
    }

    @Test
    public void testSaveEventAsAdmin_Success() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(eventoRepository.save(any(EventoEntity.class))).thenReturn(eventoMock);

        EventoEntity result = eventoService.save(eventoMock, 1L);

        assertNotNull(result);
        assertEquals(eventoMock, result);
        verify(eventoRepository, times(1)).save(eventoMock);
    }

    @Test
    public void testFindAllEvents_Success() {
        List<EventoEntity> eventoList = new ArrayList<>();
        eventoList.add(eventoMock);

        when(eventoRepository.findAll()).thenReturn(eventoList);

        List<EventoEntity> result = eventoService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(eventoMock, result.get(0));
        verify(eventoRepository, times(1)).findAll();
    }

    @Test
    public void testFindEventById_Success() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(eventoMock));

        EventoEntity result = eventoService.findById(1L);

        assertNotNull(result);
        assertEquals(eventoMock, result);
        verify(eventoRepository, times(1)).findById(1L);
    }

}