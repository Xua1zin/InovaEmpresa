package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.IdeiaService;
import com.Atividade.InovaEmpresa.entities.IdeiaEntity;
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

class IdeiaControllerTest {

    @Mock
    private IdeiaService ideiaService;

    @InjectMocks
    private IdeiaController ideiaController;

    private IdeiaEntity ideiaEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ideiaEntity = new IdeiaEntity();
        ideiaEntity.setId(1L);
        // Set other necessary properties of ideiaEntity
    }

    @Test
    void resultado_Success() {
        List<IdeiaEntity> ideias = Arrays.asList(ideiaEntity);
        when(ideiaService.resultado(anyLong())).thenReturn(ideias);

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.resultado(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ideias, response.getBody());
        verify(ideiaService, times(1)).resultado(1L);
    }

    @Test
    void resultado_Failure() {
        when(ideiaService.resultado(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.resultado(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(ideiaService, times(1)).resultado(1L);
    }

    @Test
    void save_Success() {
        when(ideiaService.save(any(IdeiaEntity.class), anyLong())).thenReturn(ideiaEntity);

        ResponseEntity<IdeiaEntity> response = ideiaController.save(ideiaEntity, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ideiaEntity, response.getBody());
        verify(ideiaService, times(1)).save(ideiaEntity, 1L);
    }

    @Test
    void save_Failure() {
        when(ideiaService.save(any(IdeiaEntity.class), anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<IdeiaEntity> response = ideiaController.save(ideiaEntity, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(ideiaService, times(1)).save(ideiaEntity, 1L);
    }

    @Test
    void findAll_Success() {
        List<IdeiaEntity> ideias = Arrays.asList(ideiaEntity);
        when(ideiaService.findAll()).thenReturn(ideias);

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ideias, response.getBody());
        verify(ideiaService, times(1)).findAll();
    }

    @Test
    void findAll_Failure() {
        when(ideiaService.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<IdeiaEntity>> response = ideiaController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(ideiaService, times(1)).findAll();
    }

    @Test
    void findById_Success() {
        when(ideiaService.findById(anyLong())).thenReturn(ideiaEntity);

        ResponseEntity<IdeiaEntity> response = ideiaController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ideiaEntity, response.getBody());
        verify(ideiaService, times(1)).findById(1L);
    }

    @Test
    void findById_Failure() {
        when(ideiaService.findById(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<IdeiaEntity> response = ideiaController.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(ideiaService, times(1)).findById(1L);
    }

    @Test
    void addColaboradores_Success() {
        List<Long> usuariosId = Arrays.asList(1L, 2L, 3L);
        when(ideiaService.addColaboradores(anyLong(), anyList())).thenReturn(ideiaEntity);

        ResponseEntity<IdeiaEntity> response = ideiaController.addColaboradores(1L, usuariosId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ideiaEntity, response.getBody());
        verify(ideiaService, times(1)).addColaboradores(1L, usuariosId);
    }

    @Test
    void addColaboradores_Failure() {
        List<Long> usuariosId = Arrays.asList(1L, 2L, 3L);
        when(ideiaService.addColaboradores(anyLong(), anyList())).thenThrow(new RuntimeException());

        ResponseEntity<IdeiaEntity> response = ideiaController.addColaboradores(1L, usuariosId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(ideiaService, times(1)).addColaboradores(1L, usuariosId);
    }
}