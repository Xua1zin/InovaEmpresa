package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.EventoRepository;
import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.EventoEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioEntity testUsuario;
    private EventoEntity testEvento;

    @BeforeEach
    void setUp() {
        testUsuario = new UsuarioEntity();
        testUsuario.setId(1L);
        testUsuario.setNome("Test User");
        testUsuario.setEmail("test@example.com");

        testEvento = new EventoEntity();
        testEvento.setId(1L);
        testEvento.setNome("Test Event");
    }

    @Test
    void testSave() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.of(testEvento));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(testUsuario);

        UsuarioEntity result = usuarioService.save(testUsuario);

        assertNotNull(result);
        assertEquals(testUsuario, result);
        assertEquals(UsuarioRole.COLABORADOR, result.getRole());
        verify(eventoRepository).save(any(EventoEntity.class));
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void testSaveWithoutEvent() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(testUsuario);

        UsuarioEntity result = usuarioService.save(testUsuario);

        assertNotNull(result);
        assertEquals(testUsuario, result);
        assertEquals(UsuarioRole.COLABORADOR, result.getRole());
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void testSaveException() {
        when(eventoRepository.findEventoAtual(any(Instant.class))).thenThrow(new RuntimeException("Test exception"));

        UsuarioEntity result = usuarioService.save(testUsuario);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testDelete() {
        doNothing().when(usuarioRepository).deleteById(anyLong());

        String result = usuarioService.delete(1L);

        assertEquals("Usuario deletedo com sucesso", result);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testDeleteException() {
        doThrow(new RuntimeException("Test exception")).when(usuarioRepository).deleteById(anyLong());

        String result = usuarioService.delete(1L);

        assertEquals("Não foi possível deletar o usuario", result);
    }

    @Test
    void testUpdate() {
        UsuarioEntity existingUser = new UsuarioEntity();
        existingUser.setId(1L);
        existingUser.setNome("Existing User");
        existingUser.setEmail("existing@example.com");
        existingUser.setRole(UsuarioRole.COLABORADOR);

        UsuarioEntity loggedInAdmin = new UsuarioEntity();
        loggedInAdmin.setId(2L);
        loggedInAdmin.setRole(UsuarioRole.ADMIN);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(loggedInAdmin));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(existingUser);

        UsuarioEntity updatedUser = new UsuarioEntity();
        updatedUser.setNome("Updated User");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setRole(UsuarioRole.JURADO);

        UsuarioEntity result = usuarioService.update(updatedUser, 2L, 1L);

        assertNotNull(result);
        assertEquals("Updated User", result.getNome());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals(UsuarioRole.JURADO, result.getRole());
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void testUpdateNonAdminUser() {
        UsuarioEntity existingUser = new UsuarioEntity();
        existingUser.setId(1L);
        existingUser.setNome("Existing User");
        existingUser.setEmail("existing@example.com");
        existingUser.setRole(UsuarioRole.COLABORADOR);

        UsuarioEntity loggedInUser = new UsuarioEntity();
        loggedInUser.setId(2L);
        loggedInUser.setRole(UsuarioRole.COLABORADOR);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(loggedInUser));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(existingUser);

        UsuarioEntity updatedUser = new UsuarioEntity();
        updatedUser.setNome("Updated User");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setRole(UsuarioRole.JURADO);

        UsuarioEntity result = usuarioService.update(updatedUser, 2L, 1L);

        assertNotNull(result);
        assertEquals("Updated User", result.getNome());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals(UsuarioRole.COLABORADOR, result.getRole());
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void testUpdateException() {
        when(usuarioRepository.findById(anyLong())).thenThrow(new RuntimeException("Test exception"));

        UsuarioEntity result = usuarioService.update(new UsuarioEntity(), 1L, 1L);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testFindAll() {
        List<UsuarioEntity> userList = List.of(testUsuario);
        when(usuarioRepository.findAll()).thenReturn(userList);

        List<UsuarioEntity> result = usuarioService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUsuario, result.get(0));
    }

    @Test
    void testFindAllException() {
        when(usuarioRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        List<UsuarioEntity> result = usuarioService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(testUsuario));

        UsuarioEntity result = usuarioService.findById(1L);

        assertNotNull(result);
        assertEquals(testUsuario, result);
    }



    @Test
    void testFindByIdException() {
        when(usuarioRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));

        UsuarioEntity result = usuarioService.findById(1L);

        assertNotNull(result);
        assertTrue(result.getId() == null);
    }

    @Test
    void testAddJurados() {
        UsuarioEntity admin = new UsuarioEntity();
        admin.setId(1L);
        admin.setRole(UsuarioRole.ADMIN);

        UsuarioEntity user1 = new UsuarioEntity();
        user1.setId(2L);
        user1.setRole(UsuarioRole.COLABORADOR);

        UsuarioEntity user2 = new UsuarioEntity();
        user2.setId(3L);
        user2.setRole(UsuarioRole.COLABORADOR);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(user1));
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(user2));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Long> userIds = List.of(2L, 3L);
        List<UsuarioEntity> result = usuarioService.addJurados(userIds, 1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(user -> user.getRole() == UsuarioRole.JURADO));
        verify(usuarioRepository, times(2)).save(any(UsuarioEntity.class));
    }

    @Test
    void testAddJuradosNonAdmin() {
        UsuarioEntity nonAdmin = new UsuarioEntity();
        nonAdmin.setId(1L);
        nonAdmin.setRole(UsuarioRole.COLABORADOR);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(nonAdmin));

        List<Long> userIds = List.of(2L, 3L);
        List<UsuarioEntity> result = usuarioService.addJurados(userIds, 1L);

        assertTrue(result.isEmpty());
        verify(usuarioRepository, never()).save(any(UsuarioEntity.class));
    }

    @Test
    void testAddJuradosException() {
        when(usuarioRepository.findById(anyLong())).thenThrow(new RuntimeException("Test exception"));

        List<Long> userIds = List.of(1L, 2L);
        List<UsuarioEntity> result = usuarioService.addJurados(userIds, 1L);

        assertTrue(result.isEmpty());
    }
}