package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Entity.CategoriaEntity;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Integer id);
}
