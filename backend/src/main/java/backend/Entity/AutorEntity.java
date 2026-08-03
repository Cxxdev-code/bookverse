package backend.Entity;

import java.time.LocalDate;
import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Autores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false) // Garante que o banco não aceite nulo
    private String nome;

    @Column(length = 5000)
    private String biografia;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor")
    private List<LivroEntity> livros;
}