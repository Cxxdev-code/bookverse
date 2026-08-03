package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Entity.LivroEntity;


public interface LivroRepository extends JpaRepository<LivroEntity, Integer> {
    
    public Optional<LivroEntity> findByIsbnAndTitulo(Integer isbn, String titulo);

    public Optional<LivroEntity> findById(Integer id);

    
    public Optional<LivroEntity> findByIsbn(Integer isbn);
    
    public List<LivroEntity> findByTituloContainingIgnoreCase(String titulo);


    public Boolean existsByTitulo(String titulo);

}
