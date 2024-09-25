package com.Atividade.InovaEmpresa.Controllers;

import com.Atividade.InovaEmpresa.Services.AvaliacaoPopularService;
import com.Atividade.InovaEmpresa.entities.AvaliacaoPopularEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AvaliacaoPopularController.class)
public class AvaliacaoPopularControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvaliacaoPopularService avaliacaoPopularService;

    private AvaliacaoPopularEntity avaliacaoPopular;

    @BeforeEach
    void setUp() {
        avaliacaoPopular = new AvaliacaoPopularEntity();
        avaliacaoPopular.setId(1L);
    }

    @Test
    void testVotar_Success() throws Exception {
        when(avaliacaoPopularService.votar(anyLong(), anyLong())).thenReturn(avaliacaoPopular);

        mockMvc.perform(post("/voto/votar")
                        .param("ideiaId", "1")
                        .param("usuarioId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testVotar_Failure() throws Exception {
        when(avaliacaoPopularService.votar(anyLong(), anyLong())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/voto/votar")
                        .param("ideiaId", "1")
                        .param("usuarioId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindById_Success() throws Exception {
        when(avaliacaoPopularService.findById(anyLong())).thenReturn(avaliacaoPopular);

        mockMvc.perform(get("/voto/findById/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Failure() throws Exception {
        when(avaliacaoPopularService.findById(anyLong())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/voto/findById/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}