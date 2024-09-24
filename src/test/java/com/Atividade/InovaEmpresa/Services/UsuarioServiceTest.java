package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private EventoRepository eventoRepository;

    private UsuarioEntity usuario;
    private EventoEntity evento;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setNome("Gustavo");
        usuario.setEmail("gustavo@example.com");
        usuario.setRole(UsuarioRole.COLABORADOR);

        evento = new EventoEntity();
        evento.setId(1L);
        evento.setNome("Evento Teste");
        evento.setUsuarios(new ArrayList<>());
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        String result = usuarioService.delete(1L);

        assertEquals("Usuario deletedo com sucesso", result);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateUsuarioAdmin() {
        UsuarioEntity logado = new UsuarioEntity();
        logado.setId(2L);
        logado.setRole(UsuarioRole.ADMIN);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(logado));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        UsuarioEntity updatedUsuario = usuarioService.update(usuario, 2L, 1L);

        assertNotNull(updatedUsuario);
        assertEquals(UsuarioRole.COLABORADOR, updatedUsuario.getRole());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void testFindAll() {
        List<UsuarioEntity> usuarios = List.of(usuario);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioEntity> foundUsuarios = usuarioService.findAll();

        assertEquals(1, foundUsuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioEntity foundUsuario = usuarioService.findById(1L);

        assertNotNull(foundUsuario);
        assertEquals("Gustavo", foundUsuario.getNome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testAddJuradosComPermissao() {
        UsuarioEntity admin = new UsuarioEntity();
        admin.setId(2L);
        admin.setRole(UsuarioRole.ADMIN);

        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(admin));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        List<UsuarioEntity> jurados = usuarioService.addJurados(List.of(1L), 2L);

        assertEquals(1, jurados.size());
        assertEquals(UsuarioRole.JURADO, jurados.get(0).getRole());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }
}