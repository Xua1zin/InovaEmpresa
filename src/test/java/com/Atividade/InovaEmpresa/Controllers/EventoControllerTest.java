package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.EventoService;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventoControllerTest {

    @Autowired
    private EventoController eventoController;

    @MockBean
    private EventoService eventoService;

    private EventoEntity testEvento;

    @BeforeEach
    void setUp() {
        testEvento = new EventoEntity();
        testEvento.setId(1L);
        testEvento.setNome("Test Event");
    }

    @Test
    void testDistribuicaoIdeiasParaJurados() {
        when(eventoService.distribuicaoIdeiasParaJurados()).thenReturn(testEvento);

        ResponseEntity<EventoEntity> response = eventoController.distribuicaoIdeiasParaJurados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testEvento, response.getBody());
    }

    @Test
    void testDistribuicaoIdeiasParaJuradosException() {
        when(eventoService.distribuicaoIdeiasParaJurados()).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<EventoEntity> response = eventoController.distribuicaoIdeiasParaJurados();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSave() {
        when(eventoService.save(any(EventoEntity.class), anyLong())).thenReturn(testEvento);

        ResponseEntity<EventoEntity> response = eventoController.save(testEvento, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testEvento, response.getBody());
    }

    @Test
    void testSaveException() {
        when(eventoService.save(any(EventoEntity.class), anyLong())).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<EventoEntity> response = eventoController.save(testEvento, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddUsuarioEvento() {
        when(eventoService.addUsuarioEvento(anyLong())).thenReturn(testEvento);

        ResponseEntity<EventoEntity> response = eventoController.save(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testEvento, response.getBody());
    }

    @Test
    void testAddUsuarioEventoException() {
        when(eventoService.addUsuarioEvento(anyLong())).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<EventoEntity> response = eventoController.save(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDelete() {
        when(eventoService.delete(anyLong())).thenReturn("Event deleted successfully");

        ResponseEntity<String> response = eventoController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Event deleted successfully", response.getBody());
    }

    @Test
    void testDeleteException() {
        when(eventoService.delete(anyLong())).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<String> response = eventoController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindAll() {
        List<EventoEntity> eventos = Arrays.asList(testEvento, new EventoEntity());
        when(eventoService.findAll()).thenReturn(eventos);

        ResponseEntity<List<EventoEntity>> response = eventoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventos, response.getBody());
    }

    @Test
    void testFindAllException() {
        when(eventoService.findAll()).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<List<EventoEntity>> response = eventoController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindById() {
        when(eventoService.findById(anyLong())).thenReturn(testEvento);

        ResponseEntity<EventoEntity> response = eventoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testEvento, response.getBody());
    }

    @Test
    void testFindByIdException() {
        when(eventoService.findById(anyLong())).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<EventoEntity> response = eventoController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}