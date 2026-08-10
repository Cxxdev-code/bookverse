package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Entity.LivroEntity;


public interface LivroRepository extends JpaRepository<LivroEntity, Integer> {
    
    public Optional<LivroEntity> findByIsbnAndTitulo(String isbn, String titulo);

    public Optional<LivroEntity> findById(Integer id);

    
    public Optional<LivroEntity> findByIsbn(String isbn);
    
    public List<LivroEntity> findByTituloContainingIgnoreCase(String titulo);


    boolean existsByTituloIgnoreCase(String titulo);

    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Integer id);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Integer id);

    boolean existsByAutor_Id(Integer autorId);

    boolean existsByCategoria_Id(Integer categoriaId);

}
