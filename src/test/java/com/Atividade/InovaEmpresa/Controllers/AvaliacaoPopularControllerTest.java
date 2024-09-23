package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoPopularService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AvaliacaoPopularControllerTest {

    @Mock
    private AvaliacaoPopularService avaliacaoPopularService;

    @InjectMocks
    private AvaliacaoPopularController avaliacaoPopularController;

    private AvaliacaoPopularEntity avaliacaoPopular;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        avaliacaoPopular = new AvaliacaoPopularEntity();
        avaliacaoPopular.setId(1L);
        // Set other necessary properties of avaliacaoPopular
    }

    @Test
    void votar_Success() {
        when(avaliacaoPopularService.votar(anyLong(), anyLong())).thenReturn(avaliacaoPopular);

        ResponseEntity<AvaliacaoPopularEntity> response = avaliacaoPopularController.votar(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoPopular, response.getBody());
        verify(avaliacaoPopularService, times(1)).votar(1L, 1L);
    }

    @Test
    void votar_Failure() {
        when(avaliacaoPopularService.votar(anyLong(), anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<AvaliacaoPopularEntity> response = avaliacaoPopularController.votar(1L, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(avaliacaoPopularService, times(1)).votar(1L, 1L);
    }

    @Test
    void findById_Success() {
        when(avaliacaoPopularService.findById(anyLong())).thenReturn(avaliacaoPopular);

        ResponseEntity<AvaliacaoPopularEntity> response = avaliacaoPopularController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoPopular, response.getBody());
        verify(avaliacaoPopularService, times(1)).findById(1L);
    }

    @Test
    void findById_Failure() {
        when(avaliacaoPopularService.findById(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<AvaliacaoPopularEntity> response = avaliacaoPopularController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(avaliacaoPopularService, times(1)).findById(1L);
    }
}