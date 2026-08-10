package backend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import backend.Entity.LivroEntity;
import backend.dto.Response.LivroCardResponse;
import backend.dto.Response.LivroDetalheResponse;
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
                .capaUrl(livro.getCapaUrl())
                .numeroPaginas(livro.getNumeroPaginas())
                .idioma(livro.getIdioma())
                .editora(livro.getEditora())
                .edicao(livro.getEdicao())
                .classificacaoEtaria(livro.getClassificacaoEtaria())
                .status(livro.getStatus())
                .destaque(livro.getDestaque())
                .criadoEm(livro.getCriadoEm())
                .atualizadoEm(livro.getAtualizadoEm())
                .build();
    }

    public LivroCardResponse converterParaCardResponse(LivroEntity livro) {
        return LivroCardResponse.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .autorId(livro.getAutor() != null ? livro.getAutor().getId() : null)
                .autor(livro.getAutor() != null ? livro.getAutor().getNome() : "Autor desconhecido")
                .categoriaId(livro.getCategoria() != null ? livro.getCategoria().getId() : null)
                .categoria(livro.getCategoria() != null ? livro.getCategoria().getNome() : "Sem categoria")
                .capaUrl(livro.getCapaUrl())
                .descricaoResumo(resumir(livro.getDescricao()))
                .publicado(livro.getDataPublicacao())
                .numeroPaginas(livro.getNumeroPaginas())
                .status(livro.getStatus())
                .destaque(livro.getDestaque())
                .build();
    }

    public LivroDetalheResponse converterParaDetalheResponse(LivroEntity livro) {
        return LivroDetalheResponse.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .descricao(livro.getDescricao())
                .isbn(livro.getIsbn())
                .autorId(livro.getAutor() != null ? livro.getAutor().getId() : null)
                .autor(livro.getAutor() != null ? livro.getAutor().getNome() : "Autor desconhecido")
                .categoriaId(livro.getCategoria() != null ? livro.getCategoria().getId() : null)
                .categoria(livro.getCategoria() != null ? livro.getCategoria().getNome() : "Sem categoria")
                .publicado(livro.getDataPublicacao())
                .capaUrl(livro.getCapaUrl())
                .numeroPaginas(livro.getNumeroPaginas())
                .idioma(livro.getIdioma())
                .editora(livro.getEditora())
                .edicao(livro.getEdicao())
                .classificacaoEtaria(livro.getClassificacaoEtaria())
                .status(livro.getStatus())
                .destaque(livro.getDestaque())
                .criadoEm(livro.getCriadoEm())
                .atualizadoEm(livro.getAtualizadoEm())
                .build();
    }

    public List<LivroResponse> converterParaListaDeResponse(List<LivroEntity> livros) {
        return livros.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    private String resumir(String descricao) {
        if (descricao == null || descricao.isBlank()) return "";
        String texto = descricao.trim();
        int limite = 180;
        return texto.length() <= limite ? texto : texto.substring(0, limite).trim() + "...";
    }
}
