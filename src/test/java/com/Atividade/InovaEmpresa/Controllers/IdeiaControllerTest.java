package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.IdeiaService;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class IdeiaControllerTest {

    @Autowired
    private IdeiaController ideiaController;

    @MockBean
    private IdeiaService ideiaService;


    @Test
    public void testResultado_Success() {
        Long eventoId = 1L;
        List<IdeiaEntity> ideiasMock = Arrays.asList(new IdeiaEntity(), new IdeiaEntity());

        when(ideiaService.resultado(eventoId)).thenReturn(ideiasMock);

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.resultado(eventoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(ideiaService, times(1)).resultado(eventoId);
    }

    @Test
    public void testResultado_Failure() {
        Long eventoId = 1L;

        when(ideiaService.resultado(eventoId)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.resultado(eventoId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(ideiaService, times(1)).resultado(eventoId);
    }


    @Test
    public void testSave_Success() {
        Long id = 1L;
        IdeiaEntity ideia = new IdeiaEntity();

        when(ideiaService.save(ideia, id)).thenReturn(ideia);

        ResponseEntity<IdeiaEntity> response = ideiaController.save(ideia, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(ideiaService, times(1)).save(ideia, id);
    }

    @Test
    public void testSave_Failure() {
        Long id = 1L;
        IdeiaEntity ideia = new IdeiaEntity();

        when(ideiaService.save(ideia, id)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<IdeiaEntity> response = ideiaController.save(ideia, id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(ideiaService, times(1)).save(ideia, id);
    }


    @Test
    public void testFindAll_Success() {
        List<IdeiaEntity> ideiasMock = Arrays.asList(new IdeiaEntity(), new IdeiaEntity());

        when(ideiaService.findAll()).thenReturn(ideiasMock);

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(ideiaService, times(1)).findAll();
    }

    @Test
    public void testFindAll_Failure() {
        when(ideiaService.findAll()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(ideiaService, times(1)).findAll();
    }


    @Test
    public void testFindById_Success() {
        Long id = 1L;
        IdeiaEntity ideiaMock = new IdeiaEntity();

        when(ideiaService.findById(id)).thenReturn(ideiaMock);

        ResponseEntity<IdeiaEntity> response = ideiaController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(ideiaService, times(1)).findById(id);
    }

    @Test
    public void testFindById_Failure() {
        Long id = 1L;

        when(ideiaService.findById(id)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<IdeiaEntity> response = ideiaController.findById(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(ideiaService, times(1)).findById(id);
    }


    @Test
    public void testAddColaboradores_Success() {
        Long ideiaId = 1L;
        List<Long> usuariosId = Arrays.asList(1L, 2L);
        IdeiaEntity ideiaMock = new IdeiaEntity();

        when(ideiaService.addColaboradores(ideiaId, usuariosId)).thenReturn(ideiaMock);

        ResponseEntity<IdeiaEntity> response = ideiaController.addColaboradores(ideiaId, usuariosId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(ideiaService, times(1)).addColaboradores(ideiaId, usuariosId);
    }

    @Test
    public void testAddColaboradores_Failure() {
        Long ideiaId = 1L;
        List<Long> usuariosId = Arrays.asList(1L, 2L);

        when(ideiaService.addColaboradores(ideiaId, usuariosId)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<IdeiaEntity> response = ideiaController.addColaboradores(ideiaId, usuariosId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(ideiaService, times(1)).addColaboradores(ideiaId, usuariosId);
    }
}
