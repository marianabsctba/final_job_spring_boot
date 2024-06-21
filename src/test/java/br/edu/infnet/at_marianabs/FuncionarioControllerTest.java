package br.edu.infnet.at_marianabs;

import br.edu.infnet.at_marianabs.controller.FuncionarioController;
import br.edu.infnet.at_marianabs.model.Funcionario;
import br.edu.infnet.at_marianabs.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
public class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testBuscarFuncionarioPorId() throws Exception {
        Funcionario funcionario = new Funcionario("Mariana BS", "Rua de teste, 123", "995556-6644", "marianabs@test.com", new Date(), null);

        Mockito.when(funcionarioService.buscarPorId(1)).thenReturn(Optional.of(funcionario));

        mockMvc.perform(get("/api/public/funcionarios/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mariana BS"))
                .andExpect(jsonPath("$.endereco").value("Rua de teste, 123"));
    }

    @Test
    public void testSalvarFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario("Mariana BS", "Rua de teste, 123", "995556-6644", "marianabs@test.com", new Date(), null);

        Mockito.when(funcionarioService.salvar(any(Funcionario.class))).thenReturn(funcionario);

        mockMvc.perform(post("/api/public/funcionarios")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAtualizarFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario("Mariana BS", "Rua de teste, 123", "995556-6644", "marianabs@test.com", new Date(), null);

        Mockito.when(funcionarioService.atualizar(eq(1), any(Funcionario.class))).thenReturn(funcionario);

        mockMvc.perform(put("/api/public/funcionarios/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mariana BS"))
                .andExpect(jsonPath("$.endereco").value("Rua de teste, 123"));
    }

    @Test
    public void testRemoverFuncionario() throws Exception {
        Mockito.doNothing().when(funcionarioService).remover(1);

        mockMvc.perform(delete("/api/public/funcionarios/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}