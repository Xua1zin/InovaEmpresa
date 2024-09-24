package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.UsuarioService;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
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
public class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioController controller;

    @Test
    public void testSave() {
        UsuarioEntity entity = new UsuarioEntity();
        when(usuarioService.save(entity)).thenReturn(entity);

        ResponseEntity<UsuarioEntity> response = controller.save(entity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        String message = "Usuário excluído com sucesso";
        when(usuarioService.delete(id)).thenReturn(message);

        ResponseEntity<String> response = controller.delete(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testUpdate() {
        Long logadoId = 1L;
        Long id = 2L;
        UsuarioEntity entity = new UsuarioEntity();
        when(usuarioService.update(entity, logadoId, id)).thenReturn(entity);

        ResponseEntity<UsuarioEntity> response = controller.update(entity, logadoId, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testFindAll() {
        List<UsuarioEntity> entities = Arrays.asList(
                new UsuarioEntity(),
                new UsuarioEntity()
        );
        when(usuarioService.findAll()).thenReturn(entities);

        ResponseEntity<List<UsuarioEntity>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entities, response.getBody());
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        UsuarioEntity entity = new UsuarioEntity();
        when(usuarioService.findById(id)).thenReturn(entity);

        ResponseEntity<UsuarioEntity> response = controller.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testAddJurados() {
        Long logadoId = 1L;
        List<Long> usuariosId = Arrays.asList(2L, 3L);
        List<UsuarioEntity> entities = Arrays.asList(
                new UsuarioEntity(),
                new UsuarioEntity()
        );
        when(usuarioService.addJurados(usuariosId, logadoId)).thenReturn(entities);

        ResponseEntity<List<UsuarioEntity>> response = controller.addJurados(usuariosId, logadoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entities, response.getBody());
    }
}