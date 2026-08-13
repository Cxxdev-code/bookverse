package backend.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Livros")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @NotBlank
    private String titulo;

    @NotBlank
    @Column(nullable = false, length = 5000)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private AutorEntity autor;

    @NotNull
    private LocalDate dataPublicacao;

    @Column(name = "capa_url", length = 2048)
    private String capaUrl;

    @Column(name = "url_leitura", length = 2048)
    private String urlLeitura;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(length = 100)
    private String idioma;

    @Column(length = 255)
    private String editora;

    @Column(length = 100)
    private String edicao;

    @Column(name = "classificacao_etaria", length = 20)
    private String classificacaoEtaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private StatusLivro status = StatusLivro.RASCUNHO;

    @Column(nullable = false)
    @Builder.Default
    private Boolean destaque = false;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;
}
