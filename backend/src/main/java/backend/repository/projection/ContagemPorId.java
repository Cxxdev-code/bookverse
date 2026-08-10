package backend.repository.projection;

/** Projeção leve para métricas sem carregar uma lista de livros por entidade. */
public interface ContagemPorId {
    Integer getId();
    Long getQuantidadeLivros();
}
