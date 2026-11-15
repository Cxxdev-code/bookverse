package com.cxxdev.bookverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cxxdev.bookverse.entity.CategoriaEntity;
import com.cxxdev.bookverse.repository.projection.ContagemPorId;

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
