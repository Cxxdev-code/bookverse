package com.cxxdev.bookverse.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cxxdev.bookverse.entity.StatusLivro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Dados completos de um livro para ler.html e futuras telas de detalhe. */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroDetalheResponse {
    private Integer id;
    private String titulo;
    private String descricao;
    private String isbn;
    private Integer autorId;
    private String autor;
    private Integer categoriaId;
    private String categoria;
    private LocalDate publicado;
    private String capaUrl;
    private String urlLeitura;
    private Integer numeroPaginas;
    private String idioma;
    private String editora;
    private String edicao;
    private String classificacaoEtaria;
    private StatusLivro status;
    private Boolean destaque;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
