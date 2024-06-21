package br.edu.infnet.at_marianabs;

import br.edu.infnet.at_marianabs.controller.DepartamentoController;
import br.edu.infnet.at_marianabs.model.Departamento;
import br.edu.infnet.at_marianabs.service.DepartamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(DepartamentoController.class)
public class DepartamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testBuscarPorIdepartamentoById() throws Exception {
        Departamento departamento = new Departamento("Biblioteca", "Infnet");

        Mockito.when(departamentoService.buscarPorId(1)).thenReturn(Optional.of(departamento));

        mockMvc.perform(get("/api/public/departamentos/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Biblioteca"))
                .andExpect(jsonPath("$.local").value("Infnet"));
    }

    @Test
    public void testSalvarDepartamento() throws Exception {
        Departamento departamento = new Departamento("Biblioteca", "Infnet");

        Mockito.when(departamentoService.salvar(any(Departamento.class))).thenReturn(departamento);

        mockMvc.perform(post("/api/public/departamentos")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isCreated());
    }


    @Test
    public void testAtualizarDepartamento() throws Exception {
        Departamento departamento = new Departamento("Biblioteca", "Infnet");

        Mockito.when(departamentoService.atualizar(eq(1), any(Departamento.class))).thenReturn(departamento);

        mockMvc.perform(put("/api/public/departamentos/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Biblioteca"))
                .andExpect(jsonPath("$.local").value("Infnet"));
    }

    @Test
    public void testRemoverDepartamento() throws Exception {
        Mockito.doNothing().when(departamentoService).remover(1);

        mockMvc.perform(delete("/api/public/departamentos/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
