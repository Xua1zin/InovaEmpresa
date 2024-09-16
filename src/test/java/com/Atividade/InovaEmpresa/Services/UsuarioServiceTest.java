package com.Atividade.InovaEmpresa.Services;

import com.Atividade.InovaEmpresa.Repositories.UsuarioRepository;
import com.Atividade.InovaEmpresa.entities.UsuarioEntity;
import com.Atividade.InovaEmpresa.entities.UsuarioRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSuccess() {
        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        UsuarioEntity savedUsuario = usuarioService.save(usuario);

        assertNotNull(savedUsuario);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testSaveFailure() {
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new RuntimeException("Erro"));

        UsuarioEntity savedUsuario = usuarioService.save(new UsuarioEntity());

        assertEquals(new UsuarioEntity(), savedUsuario);
    }

    @Test
    void testDeleteSuccess() {
        doNothing().when(usuarioRepository).deleteById(anyLong());

        String result = usuarioService.delete(1L);

        assertEquals("Usuario deletedo com sucesso", result);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFailure() {
        doThrow(new RuntimeException("Erro")).when(usuarioRepository).deleteById(anyLong());

        String result = usuarioService.delete(1L);

        assertEquals("Não foi possível deletar o usuario", result);
    }

    @Test
    void testUpdateSuccess() {
        UsuarioEntity existingUser = new UsuarioEntity();
        existingUser.setId(1L);
        existingUser.setNome("João");
        existingUser.setRole(UsuarioRole.COLABORADOR);

        UsuarioEntity adminUser = new UsuarioEntity();
        adminUser.setId(2L);
        adminUser.setNome("Admin");
        adminUser.setRole(UsuarioRole.ADMIN);

        UsuarioEntity newUser = new UsuarioEntity();
        newUser.setNome("João Atualizado");
        newUser.setRole(UsuarioRole.ADMIN);

        // Mocking repository methods
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(adminUser));

        // Mock save to return the updated user with the role changed
        existingUser.setRole(UsuarioRole.ADMIN); // Simulate role change
        when(usuarioRepository.save(existingUser)).thenReturn(existingUser);

        // Call the service method
        UsuarioEntity updatedUser = usuarioService.update(newUser, 2L, 1L);

        // Verify results
        assertEquals(UsuarioRole.ADMIN, updatedUser.getRole());
        assertEquals("João Atualizado", updatedUser.getNome());
    }

    @Test
    void testUpdateFailure() {
        when(usuarioRepository.findById(anyLong())).thenThrow(new RuntimeException("Erro"));

        UsuarioEntity result = usuarioService.update(new UsuarioEntity(), 1L, 1L);

        assertEquals(new UsuarioEntity(), result);
    }

    @Test
    void testFindAllSuccess() {
        List<UsuarioEntity> usuarios = new ArrayList<>();
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioEntity> result = usuarioService.findAll();

        assertEquals(usuarios, result);
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindAllFailure() {
        when(usuarioRepository.findAll()).thenThrow(new RuntimeException("Erro"));

        List<UsuarioEntity> result = usuarioService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdSuccess() {
        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        UsuarioEntity result = usuarioService.findById(1L);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdFailure() {
        when(usuarioRepository.findById(anyLong())).thenThrow(new RuntimeException("Erro"));

        UsuarioEntity result = usuarioService.findById(1L);
        UsuarioEntity expected = new UsuarioEntity();
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getNome(), result.getNome());
    }


    @Test
    void testAddJuradosSuccess() {
        List<Long> usuariosId = List.of(1L, 2L);
        UsuarioEntity admin = new UsuarioEntity();
        admin.setRole(UsuarioRole.ADMIN);

        UsuarioEntity usuario1 = new UsuarioEntity();
        usuario1.setRole(UsuarioRole.COLABORADOR);

        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setRole(UsuarioRole.COLABORADOR);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario2));
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(admin));

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(invocation -> {
            UsuarioEntity user = invocation.getArgument(0);
            if (usuariosId.contains(user.getId())) {
                user.setRole(UsuarioRole.JURADO); // Simulate role change
            }
            return user;
        });

        List<UsuarioEntity> result = usuarioService.addJurados(usuariosId, 3L);

        assertEquals(2, result.size());
        assertEquals(UsuarioRole.JURADO, result.get(0).getRole());
        assertEquals(UsuarioRole.JURADO, result.get(1).getRole());
    }


    @Test
    void testAddJuradosFailure() {
        List<Long> usuariosId = List.of(1L, 2L);
        UsuarioEntity admin = new UsuarioEntity();
        admin.setRole(UsuarioRole.COLABORADOR);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(admin));

        List<UsuarioEntity> result = usuarioService.addJurados(usuariosId, 1L);

        assertTrue(result.isEmpty());
    }
}
