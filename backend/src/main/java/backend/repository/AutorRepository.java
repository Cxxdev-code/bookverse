package backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.Entity.AutorEntity;
import backend.repository.projection.ContagemPorId;

public interface AutorRepository extends JpaRepository<AutorEntity, Integer> {

    // Verifica se já existe um autor cadastrado com o mesmo nome
    boolean existsByNome(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Integer id);

    
    

    // Permite buscar autores filtrando pelo nome (case insensitive)
    List<AutorEntity> findByNomeContainingIgnoreCase(String nome);

    @Query("""
            select a.id as id, count(l.id) as quantidadeLivros
            from AutorEntity a left join a.livros l
            group by a.id
            """)
    List<ContagemPorId> contarLivrosPorAutor();
}
