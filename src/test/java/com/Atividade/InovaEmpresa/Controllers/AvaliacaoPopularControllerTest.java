package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoPopularService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AvaliacaoPopularControllerTest {

    @MockBean
    AvaliacaoPopularService avaliacaoPopularService;

    @Autowired
    AvaliacaoPopularController avaliacaoPopularController;

    @Test
    void testVotar() {
        long ideiaId = 1L;
        long usuarioId = 2L;
        AvaliacaoPopularEntity entity = new AvaliacaoPopularEntity();
        when(avaliacaoPopularService.votar(ideiaId, usuarioId)).thenReturn(entity);

        ResponseEntity<AvaliacaoPopularEntity> response = avaliacaoPopularController.votar(ideiaId, usuarioId);

        assertEquals(entity, response.getBody());
        verify(avaliacaoPopularService, times(1)).votar(ideiaId, usuarioId);
    }

    @Test
    void testFindById() {
        long id = 1L;
        AvaliacaoPopularEntity entity = new AvaliacaoPopularEntity();
        when(avaliacaoPopularService.findById(id)).thenReturn(entity);

        ResponseEntity<AvaliacaoPopularEntity> response = avaliacaoPopularController.findById(id);

        assertEquals(entity, response.getBody());
        verify(avaliacaoPopularService, times(1)).findById(id);
    }
}