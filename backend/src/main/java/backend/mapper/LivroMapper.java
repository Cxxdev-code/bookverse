package backend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import backend.Entity.LivroEntity;
import backend.dto.Response.LivroResponse;

@Component
public class LivroMapper {

    // O método editarEntidade agora será responsabilidade do Service para garantir
    // a validação das entidades Autor e Categoria antes de setar.

    public LivroResponse converterParaResponse(LivroEntity livro) {
        return LivroResponse.builder()
                .id(livro.getId())
                .isbn(livro.getIsbn())
                .titulo(livro.getTitulo())
                .descricao(livro.getDescricao())
                .autorId(livro.getAutor() != null ? livro.getAutor().getId() : null)
                // Acessamos o objeto relacional e pegamos a string amigável
                .autor(livro.getAutor() != null ? livro.getAutor().getNome() : "Autor Desconhecido")
                .categoriaId(livro.getCategoria() != null ? livro.getCategoria().getId() : null)
                .categoria(livro.getCategoria() != null ? livro.getCategoria().getNome() : "Sem Categoria")
                .publicado(livro.getDataPublicacao())
                .build();
    }

    public List<LivroResponse> converterParaListaDeResponse(List<LivroEntity> livros) {
        return livros.stream()
                .map(this::converterParaResponse)
                .toList();
    }
}
