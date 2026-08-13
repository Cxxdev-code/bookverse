package backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:seguranca_teste;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
class SegurancaIntegracaoTest {
    @Autowired private MockMvc mockMvc;

    @Test
    void protegeCatalogoESeparaPermissoesDeUsuarioEAdministrador() throws Exception {
        mockMvc.perform(get("/api/home")).andExpect(status().isUnauthorized());

        String respostaRegistro = mockMvc.perform(post("/api/auth/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"nome":"Leitor de teste","email":"leitor@teste.com","senha":"senha123",
                     "sexo":"Prefiro não informar","dataNascimento":"2000-01-01"}
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuario.papel").value("USUARIO"))
                .andReturn().getResponse().getContentAsString();
        String tokenUsuario = JsonPath.read(respostaRegistro, "$.token");

        mockMvc.perform(get("/api/home").header("Authorization", "Bearer " + tokenUsuario))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/admin/usuarios").header("Authorization", "Bearer " + tokenUsuario))
                .andExpect(status().isForbidden());

        String respostaAdmin = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@bookverse.local\",\"senha\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.papel").value("ADMIN"))
                .andReturn().getResponse().getContentAsString();
        String tokenAdmin = JsonPath.read(respostaAdmin, "$.token");

        mockMvc.perform(get("/api/admin/usuarios").header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").exists());
    }
}
