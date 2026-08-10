package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import backend.Entity.LivroEntity;
import backend.Entity.StatusLivro;


public interface LivroRepository extends JpaRepository<LivroEntity, Integer>, JpaSpecificationExecutor<LivroEntity> {
    
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

    long countByAutor_Id(Integer autorId);

    long countByCategoria_Id(Integer categoriaId);

    long countByStatus(StatusLivro status);

    @EntityGraph(attributePaths = { "autor", "categoria" })
    Page<LivroEntity> findAll(Specification<LivroEntity> specification, Pageable pageable);

    @EntityGraph(attributePaths = { "autor", "categoria" })
    List<LivroEntity> findTop6ByStatusOrderByDataPublicacaoDesc(StatusLivro status);

    @EntityGraph(attributePaths = { "autor", "categoria" })
    List<LivroEntity> findTop6ByStatusAndDestaqueTrueOrderByDataPublicacaoDesc(StatusLivro status);

}
