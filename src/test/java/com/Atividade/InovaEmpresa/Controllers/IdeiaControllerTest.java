package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.IdeiaService;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class IdeiaControllerTest {

    @MockBean
    private IdeiaService ideiaService;

    @Autowired
    IdeiaController controller;

    @Test
    public void testResultado() {
        Long eventoId = 1L;
        List<IdeiaEntity> entities = Arrays.asList(
                new IdeiaEntity(),
                new IdeiaEntity()
        );
        when(ideiaService.resultado(eventoId)).thenReturn(entities);

        ResponseEntity<List<IdeiaEntity>> response = controller.resultado(eventoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entities, response.getBody());
    }

    @Test
    public void testSave() {
        Long id = 1L;
        IdeiaEntity entity = new IdeiaEntity();
        when(ideiaService.save(entity, id)).thenReturn(entity);

        ResponseEntity<IdeiaEntity> response = controller.save(entity, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testFindAll() {
        List<IdeiaEntity> entities = Arrays.asList(
                new IdeiaEntity(),
                new IdeiaEntity()
        );
        when(ideiaService.findAll()).thenReturn(entities);

        ResponseEntity<List<IdeiaEntity>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entities, response.getBody());
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        IdeiaEntity entity = new IdeiaEntity();
        when(ideiaService.findById(id)).thenReturn(entity);

        ResponseEntity<IdeiaEntity> response = controller.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testAddColaboradores() {
        Long ideiaId = 1L;
        List<Long> usuariosId = Arrays.asList(2L, 3L);
        IdeiaEntity entity = new IdeiaEntity();
        when(ideiaService.addColaboradores(ideiaId, usuariosId)).thenReturn(entity);

        ResponseEntity<IdeiaEntity> response = controller.addColaboradores(ideiaId, usuariosId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }
}