package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.EventoService;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EventoControllerTest {

    @Mock
    private EventoService eventoService;

    @InjectMocks
    private EventoController eventoController;

    private EventoEntity eventoEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventoEntity = new EventoEntity();
        eventoEntity.setId(1L);
    }

    @Test
    void distribuicaoIdeiasParaJurados_Success() {
        when(eventoService.distribuicaoIdeiasParaJurados()).thenReturn(eventoEntity);

        ResponseEntity<EventoEntity> response = eventoController.distribuicaoIdeiasParaJurados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventoEntity, response.getBody());
        verify(eventoService, times(1)).distribuicaoIdeiasParaJurados();
    }

    @Test
    void distribuicaoIdeiasParaJurados_Failure() {
        when(eventoService.distribuicaoIdeiasParaJurados()).thenThrow(new RuntimeException());

        ResponseEntity<EventoEntity> response = eventoController.distribuicaoIdeiasParaJurados();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(eventoService, times(1)).distribuicaoIdeiasParaJurados();
    }

    @Test
    void save_Success() {
        when(eventoService.save(any(EventoEntity.class), anyLong())).thenReturn(eventoEntity);

        ResponseEntity<EventoEntity> response = eventoController.save(eventoEntity, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventoEntity, response.getBody());
        verify(eventoService, times(1)).save(eventoEntity, 1L);
    }

    @Test
    void save_Failure() {
        when(eventoService.save(any(EventoEntity.class), anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<EventoEntity> response = eventoController.save(eventoEntity, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(eventoService, times(1)).save(eventoEntity, 1L);
    }

    @Test
    void delete_Success() {
        when(eventoService.delete(anyLong())).thenReturn("Deleted successfully");

        ResponseEntity<String> response = eventoController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted successfully", response.getBody());
        verify(eventoService, times(1)).delete(1L);
    }

    @Test
    void delete_Failure() {
        when(eventoService.delete(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<String> response = eventoController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(eventoService, times(1)).delete(1L);
    }

    @Test
    void findAll_Success() {
        List<EventoEntity> eventos = Arrays.asList(eventoEntity);
        when(eventoService.findAll()).thenReturn(eventos);

        ResponseEntity<List<EventoEntity>> response = eventoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventos, response.getBody());
        verify(eventoService, times(1)).findAll();
    }

    @Test
    void findAll_Failure() {
        when(eventoService.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<EventoEntity>> response = eventoController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(eventoService, times(1)).findAll();
    }

    @Test
    void findById_Success() {
        when(eventoService.findById(anyLong())).thenReturn(eventoEntity);

        ResponseEntity<EventoEntity> response = eventoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventoEntity, response.getBody());
        verify(eventoService, times(1)).findById(1L);
    }

    @Test
    void findById_Failure() {
        when(eventoService.findById(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<EventoEntity> response = eventoController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(eventoService, times(1)).findById(1L);
    }
}