package backend.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios") 
@NoArgsConstructor
@AllArgsConstructor 
@Getter
@Setter
@Builder
public class UsuarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String sexo;

    @NotNull 
    private LocalDate dataNascimento;

    @NotNull
    private Integer matricula;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "senha_hash", length = 100)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private PapelUsuario papel = PapelUsuario.USUARIO;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "imagem_perfil_url", length = 2048)
    private String imagemPerfilUrl;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "ultimo_acesso_em")
    private LocalDateTime ultimoAcessoEm;

    // A mágica acontece aqui!
    @Transient // Isso faz o JPA/Hibernate não criar a coluna 'idade' no banco
    public Integer getIdade() {
        if (this.dataNascimento == null) {
            return null; // Retorna null se não tiver data para evitar quebrar o código
        }
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }
}
