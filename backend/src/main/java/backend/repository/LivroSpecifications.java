package backend.repository;

import org.springframework.data.jpa.domain.Specification;

import backend.Entity.LivroEntity;
import backend.Entity.StatusLivro;

/** Filtros combináveis do catálogo público. */
public final class LivroSpecifications {
    private LivroSpecifications() {
    }

    public static Specification<LivroEntity> publicado() {
        return (root, query, builder) -> builder.equal(root.get("status"), StatusLivro.PUBLICADO);
    }

    public static Specification<LivroEntity> textoContem(String busca) {
        if (busca == null || busca.isBlank()) return (root, query, builder) -> builder.conjunction();
        String termo = "%" + busca.trim().toLowerCase() + "%";
        return (root, query, builder) -> builder.or(
                builder.like(builder.lower(root.get("titulo")), termo),
                builder.like(builder.lower(root.get("isbn")), termo),
                builder.like(builder.lower(root.get("descricao")), termo),
                builder.like(builder.lower(root.join("autor").get("nome")), termo),
                builder.like(builder.lower(root.join("categoria").get("nome")), termo));
    }

    public static Specification<LivroEntity> categoriaIgual(Integer categoriaId) {
        if (categoriaId == null) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.equal(root.get("categoria").get("id"), categoriaId);
    }

    public static Specification<LivroEntity> autorIgual(Integer autorId) {
        if (autorId == null) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.equal(root.get("autor").get("id"), autorId);
    }
}
