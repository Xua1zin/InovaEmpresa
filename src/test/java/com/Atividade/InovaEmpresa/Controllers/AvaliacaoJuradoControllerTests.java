package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoJuradoService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AvaliacaoJuradoControllerTests {

    @Mock
    private AvaliacaoJuradoService avaliacaoJuradoService;

    @InjectMocks
    private AvaliacaoJuradoController avaliacaoJuradoController;

    private AvaliacaoJuradoEntity avaliacaoJurado;

    @BeforeEach
    void setUp() {
        avaliacaoJurado = new AvaliacaoJuradoEntity();
        avaliacaoJurado.setId(1L);
        avaliacaoJurado.setNota(8.5);
    }

    @Test
    void testVerNota_Success() {
        when(avaliacaoJuradoService.verNota(1L)).thenReturn(8.5);

        ResponseEntity<Double> response = avaliacaoJuradoController.verNota(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(8.5, response.getBody());
    }

    @Test
    void testVerNota_Exception() {
        when(avaliacaoJuradoService.verNota(1L)).thenThrow(new RuntimeException());

        ResponseEntity<Double> response = avaliacaoJuradoController.verNota(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSave_Success() {
        when(avaliacaoJuradoService.save(1L, 1L, 8.5)).thenReturn(avaliacaoJurado);

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.save(1L, 1L, 8.5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoJurado, response.getBody());
    }

    @Test
    void testSave_Exception() {
        when(avaliacaoJuradoService.save(1L, 1L, 8.5)).thenThrow(new RuntimeException());

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.save(1L, 1L, 8.5);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindAll_Success() {
        List<AvaliacaoJuradoEntity> avaliacoes = Arrays.asList(avaliacaoJurado);
        when(avaliacaoJuradoService.findAll()).thenReturn(avaliacoes);

        ResponseEntity<List<AvaliacaoJuradoEntity>> response = avaliacaoJuradoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacoes, response.getBody());
    }

    @Test
    void testFindAll_Exception() {
        when(avaliacaoJuradoService.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<AvaliacaoJuradoEntity>> response = avaliacaoJuradoController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindById_Success() {
        when(avaliacaoJuradoService.findById(1L)).thenReturn(avaliacaoJurado);

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoJurado, response.getBody());
    }

    @Test
    void testFindById_Exception() {
        when(avaliacaoJuradoService.findById(1L)).thenThrow(new RuntimeException());

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}