package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.Entity.CategoriaEntity;
import backend.repository.projection.ContagemPorId;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Integer id);

    @Query("""
            select c.id as id, count(l.id) as quantidadeLivros
            from CategoriaEntity c left join c.livros l
            group by c.id
            """)
    java.util.List<ContagemPorId> contarLivrosPorCategoria();
}
