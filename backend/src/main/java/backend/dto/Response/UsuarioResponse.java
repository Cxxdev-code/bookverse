package backend.dto.Response;



import java.time.LocalDate;
import java.time.LocalDateTime;

import backend.Entity.PapelUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor 
@Setter
@Getter
@Builder
public class UsuarioResponse {

    private Integer id;
    
    private String nome;

    private String sexo;

    private Integer idade;
    
    private LocalDate dataNascimento;

    private Integer matricula;

    private String email;
    private PapelUsuario papel;
    private Boolean ativo;
    private String imagemPerfilUrl;
    private LocalDateTime criadoEm;
    private LocalDateTime ultimoAcessoEm;


}
