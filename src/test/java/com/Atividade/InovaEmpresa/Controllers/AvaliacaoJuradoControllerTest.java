package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Controllers.AvaliacaoJuradoController;
import com.Atividade.InovaEmpresa.Services.AvaliacaoJuradoService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoJuradoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AvaliacaoJuradoControllerTest {

    @MockBean
    private AvaliacaoJuradoService avaliacaoJuradoService;

    @Autowired
    private AvaliacaoJuradoController avaliacaoJuradoController;

    @Test
    void testVerNota() {
        long id = 1L;
        double nota = 4.5;
        when(avaliacaoJuradoService.verNota(id)).thenReturn(nota);

        ResponseEntity<Double> response = avaliacaoJuradoController.verNota(id);

        assertEquals(nota, response.getBody());
        verify(avaliacaoJuradoService, times(1)).verNota(id);
    }

    @Test
    void testSave() {
        long ideiaId = 1L;
        long usuarioId = 2L;
        double nota = 4.5;
        AvaliacaoJuradoEntity entity = new AvaliacaoJuradoEntity();
        when(avaliacaoJuradoService.save(ideiaId, usuarioId, nota)).thenReturn(entity);

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.save(ideiaId, usuarioId, nota);

        assertEquals(entity, response.getBody());
        verify(avaliacaoJuradoService, times(1)).save(ideiaId, usuarioId, nota);
    }

    @Test
    void testFindAll() {
        List<AvaliacaoJuradoEntity> entities = new ArrayList<>();
        when(avaliacaoJuradoService.findAll()).thenReturn(entities);

        ResponseEntity<List<AvaliacaoJuradoEntity>> response = avaliacaoJuradoController.findAll();

        assertEquals(entities, response.getBody());
        verify(avaliacaoJuradoService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long id = 1L;
        AvaliacaoJuradoEntity entity = new AvaliacaoJuradoEntity();
        when(avaliacaoJuradoService.findById(id)).thenReturn(entity);

        ResponseEntity<AvaliacaoJuradoEntity> response = avaliacaoJuradoController.findById(id);

        assertEquals(entity, response.getBody());
        verify(avaliacaoJuradoService, times(1)).findById(id);
    }
}