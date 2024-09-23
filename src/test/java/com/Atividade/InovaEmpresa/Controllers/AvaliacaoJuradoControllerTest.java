package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoJuradoService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
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

class AvaliacaoJuradoControllerTest {

    @Mock
    private AvaliacaoJuradoService avaliacaoJuradoService;

    @InjectMocks
    private AvaliacaoJuradoController avaliacaoJuradoController;

    private AvaliacaoJuradoEntity avaliacaoJurado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        avaliacaoJurado = new AvaliacaoJuradoEntity();
        avaliacaoJurado.setId(1L);
        avaliacaoJurado.setNota(8.5);
    }

    @Test
    void verNota_Success() {
        when(avaliacaoJuradoService.verNota(anyLong())).thenReturn(8.5);

        ResponseEntity<Double> response = avaliacaoJuradoController.verNota(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(8.5, response.getBody());
    }

    @Test
    void verNota_Failure() {
        when(avaliacaoJuradoService.verNota(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<Double> response = avaliacaoJuradoController.verNota(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void save_Success() {
        when(avaliacaoJuradoService.save(anyLong(), anyLong(), anyDouble())).thenReturn(avaliacaoJurado);

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.save(1L, 1L, 8.5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoJurado, response.getBody());
    }

    @Test
    void save_Failure() {
        when(avaliacaoJuradoService.save(anyLong(), anyLong(), anyDouble())).thenThrow(new RuntimeException());

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.save(1L, 1L, 8.5);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void findAll_Success() {
        List<AvaliacaoJuradoEntity> avaliacoes = Arrays.asList(avaliacaoJurado);
        when(avaliacaoJuradoService.findAll()).thenReturn(avaliacoes);

        ResponseEntity<List<AvaliacaoJuradoEntity>> response = avaliacaoJuradoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacoes, response.getBody());
    }

    @Test
    void findAll_Failure() {
        when(avaliacaoJuradoService.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<AvaliacaoJuradoEntity>> response = avaliacaoJuradoController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void findById_Success() {
        when(avaliacaoJuradoService.findById(anyLong())).thenReturn(avaliacaoJurado);

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoJurado, response.getBody());
    }

    @Test
    void findById_Failure() {
        when(avaliacaoJuradoService.findById(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}