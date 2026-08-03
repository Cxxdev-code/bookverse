package backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Entity.AutorEntity;

public interface AutorRepository extends JpaRepository<AutorEntity, Integer> {

    // Verifica se já existe um autor cadastrado com o mesmo nome
    boolean existsByNome(String nome);

    
    

    // Permite buscar autores filtrando pelo nome (case insensitive)
    List<AutorEntity> findByNomeContainingIgnoreCase(String nome);
}