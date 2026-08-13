package backend.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import backend.Entity.UsuarioEntity;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    
    long countAllByMatriculaIsNotNull();

    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);

    List<UsuarioEntity> findAllByOrderByCriadoEmDesc();




}
