package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.UsuarioService;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @MockBean
    private UsuarioService usuarioService;


    @Test
    public void testSave_Success() {
        UsuarioEntity usuario = new UsuarioEntity();

        when(usuarioService.save(usuario)).thenReturn(usuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.save(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).save(usuario);
    }

    @Test
    public void testSave_Failure() {
        UsuarioEntity usuario = new UsuarioEntity();

        when(usuarioService.save(usuario)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<UsuarioEntity> response = usuarioController.save(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).save(usuario);
    }


    @Test
    public void testDelete_Success() {
        Long id = 1L;

        when(usuarioService.delete(id)).thenReturn("Deleted");

        ResponseEntity<String> response = usuarioController.delete(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
        verify(usuarioService, times(1)).delete(id);
    }

    @Test
    public void testDelete_Failure() {
        Long id = 1L;

        when(usuarioService.delete(id)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = usuarioController.delete(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).delete(id);
    }


    @Test
    public void testUpdate_Success() {
        Long logadoId = 1L;
        Long id = 2L;
        UsuarioEntity usuario = new UsuarioEntity();

        when(usuarioService.update(usuario, logadoId, id)).thenReturn(usuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.update(usuario, logadoId, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).update(usuario, logadoId, id);
    }

    @Test
    public void testUpdate_Failure() {
        Long logadoId = 1L;
        Long id = 2L;
        UsuarioEntity usuario = new UsuarioEntity();

        when(usuarioService.update(usuario, logadoId, id)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<UsuarioEntity> response = usuarioController.update(usuario, logadoId, id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).update(usuario, logadoId, id);
    }


    @Test
    public void testFindAll_Success() {
        List<UsuarioEntity> usuarios = Arrays.asList(new UsuarioEntity(), new UsuarioEntity());

        when(usuarioService.findAll()).thenReturn(usuarios);

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(usuarioService, times(1)).findAll();
    }

    @Test
    public void testFindAll_Failure() {
        when(usuarioService.findAll()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).findAll();
    }


    @Test
    public void testFindById_Success() {
        Long id = 1L;
        UsuarioEntity usuario = new UsuarioEntity();

        when(usuarioService.findById(id)).thenReturn(usuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).findById(id);
    }

    @Test
    public void testFindById_Failure() {
        Long id = 1L;

        when(usuarioService.findById(id)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<UsuarioEntity> response = usuarioController.findById(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).findById(id);
    }


    @Test
    public void testAddJurados_Success() {
        Long logadoId = 1L;
        List<Long> usuariosId = Arrays.asList(1L, 2L);
        List<UsuarioEntity> usuariosMock = Arrays.asList(new UsuarioEntity(), new UsuarioEntity());

        when(usuarioService.addJurados(usuariosId, logadoId)).thenReturn(usuariosMock);

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.addJurados(usuariosId, logadoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(usuarioService, times(1)).addJurados(usuariosId, logadoId);
    }

    @Test
    public void testAddJurados_Failure() {
        Long logadoId = 1L;
        List<Long> usuariosId = Arrays.asList(1L, 2L);

        when(usuarioService.addJurados(usuariosId, logadoId)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.addJurados(usuariosId, logadoId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).addJurados(usuariosId, logadoId);
    }
}
