package backend.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import backend.Entity.UsuarioEntity;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    
    long countAllByMatriculaIsNotNull();

    UsuarioEntity findById(String id);

    List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);




}
