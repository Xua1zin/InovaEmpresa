package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.EventoService;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventoControllerTest {

    @MockBean
    EventoService eventoService;

    @Autowired
    EventoController eventoController;

    @Test
    void testDistribuicaoIdeiasParaJurados() {
        EventoEntity entity = new EventoEntity();
        when(eventoService.distribuicaoIdeiasParaJurados()).thenReturn(entity);

        ResponseEntity<EventoEntity> response = eventoController.distribuicaoIdeiasParaJurados();

        assertEquals(entity, response.getBody());
        verify(eventoService, times(1)).distribuicaoIdeiasParaJurados();
    }

    @Test
    void testSaveEvento() {
        EventoEntity entity = new EventoEntity();
        long id = 1L;
        when(eventoService.save(entity, id)).thenReturn(entity);

        ResponseEntity<EventoEntity> response = eventoController.save(entity, id);

        assertEquals(entity, response.getBody());
        verify(eventoService, times(1)).save(entity, id);
    }

    @Test
    void testAddUsuarioEvento() {
        long usuarioId = 1L;
        EventoEntity entity = new EventoEntity();
        when(eventoService.addUsuarioEvento(usuarioId)).thenReturn(entity);

        ResponseEntity<EventoEntity> response = eventoController.save(usuarioId);

        assertEquals(entity, response.getBody());
        verify(eventoService, times(1)).addUsuarioEvento(usuarioId);
    }

    @Test
    void testDelete() {
        long id = 1L;
        String message = "Entity deleted";
        when(eventoService.delete(id)).thenReturn(message);

        ResponseEntity<String> response = eventoController.delete(id);

        assertEquals(message, response.getBody());
        verify(eventoService, times(1)).delete(id);
    }

    @Test
    void testFindAll() {
        List<EventoEntity> entities = new ArrayList<>();
        when(eventoService.findAll()).thenReturn(entities);

        ResponseEntity<List<EventoEntity>> response = eventoController.findAll();

        assertEquals(entities, response.getBody());
        verify(eventoService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long id = 1L;
        EventoEntity entity = new EventoEntity();
        when(eventoService.findById(id)).thenReturn(entity);

        ResponseEntity<EventoEntity> response = eventoController.findById(id);

        assertEquals(entity, response.getBody());
        verify(eventoService, times(1)).findById(id);
    }
}