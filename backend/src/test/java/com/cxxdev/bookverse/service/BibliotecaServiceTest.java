package com.cxxdev.bookverse.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;

import com.cxxdev.bookverse.dto.request.LivroDto;
import com.cxxdev.bookverse.dto.response.LivroCardResponse;
import com.cxxdev.bookverse.dto.response.PageResponse;
import com.cxxdev.bookverse.entity.AutorEntity;
import com.cxxdev.bookverse.entity.CategoriaEntity;
import com.cxxdev.bookverse.entity.LivroEntity;
import com.cxxdev.bookverse.entity.StatusLivro;
import com.cxxdev.bookverse.exception.RequisicaoInvalidaException;
import com.cxxdev.bookverse.exception.autor.AutorIdNaoEncontrado;
import com.cxxdev.bookverse.exception.livro.LivroJaExistenteException;
import com.cxxdev.bookverse.exception.livro.LivroNaoEncontradoPorIdException;
import com.cxxdev.bookverse.mapper.LivroMapper;
import com.cxxdev.bookverse.repository.AutorRepository;
import com.cxxdev.bookverse.repository.CategoriaRepository;
import com.cxxdev.bookverse.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class BibliotecaServiceTest {

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private BibliotecaService bibliotecaService;

    @Captor
    private ArgumentCaptor<LivroEntity> livroCaptor;

    private LivroDto livroDto;

    @BeforeEach
    void setUp() {
        livroDto = LivroDto.builder()
                .titulo("Dom Casmurro")
                .descricao("Romance de Machado de Assis")
                .isbn("9788535910663")
                .publicado(LocalDate.of(1899, 1, 1))
                .autorId(1)
                .categoriaId(2)
                .build();
    }

    @Test
    @DisplayName("buscarCatalogo deve rejeitar página maior que o tamanho máximo")
    void buscarCatalogoDeveRejeitarPaginaMaiorQueMaximo() {
        assertThatThrownBy(() -> bibliotecaService.buscarCatalogo(0, 51, null, null, null, "recentes"))
                .isInstanceOf(RequisicaoInvalidaException.class)
                .hasMessageContaining("50");
    }

    @Test
    @DisplayName("buscarCatalogo deve rejeitar ordenação desconhecida")
    void buscarCatalogoDeveRejeitarOrdenacaoDesconhecida() {
        assertThatThrownBy(() -> bibliotecaService.buscarCatalogo(0, 10, null, null, null, "aleatoria"))
                .isInstanceOf(RequisicaoInvalidaException.class)
                .hasMessageContaining("ordenação");
    }

    @Test
    @DisplayName("buscarCatalogo deve retornar página mapeada para cards")
    @SuppressWarnings("unchecked")
    void buscarCatalogoDeveRetornarPaginaMapeada() {
        LivroEntity livro = LivroEntity.builder().titulo("Dom Casmurro").build();
        LivroCardResponse card = mock(LivroCardResponse.class);
        when(livroRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(livro)));
        when(livroMapper.converterParaCardResponse(livro)).thenReturn(card);

        PageResponse<LivroCardResponse> resposta =
                bibliotecaService.buscarCatalogo(0, 10, "casmurro", null, null, "recentes");

        assertThat(resposta.getContent()).containsExactly(card);
        assertThat(resposta.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("adicionarLivro deve rejeitar título duplicado")
    void adicionarLivroDeveRejeitarTituloDuplicado() {
        when(livroRepository.existsByTituloIgnoreCase("Dom Casmurro")).thenReturn(true);

        assertThatThrownBy(() -> bibliotecaService.adicionarLivro(livroDto))
                .isInstanceOf(LivroJaExistenteException.class);
        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("adicionarLivro deve falhar quando o autor não existe")
    void adicionarLivroDeveFalharQuandoAutorNaoExiste() {
        when(livroRepository.existsByTituloIgnoreCase(any())).thenReturn(false);
        when(livroRepository.existsByIsbn(any())).thenReturn(false);
        when(autorRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bibliotecaService.adicionarLivro(livroDto))
                .isInstanceOf(AutorIdNaoEncontrado.class);
    }

    @Test
    @DisplayName("adicionarLivro sem status deve salvar como PUBLICADO")
    void adicionarLivroSemStatusDeveSalvarComoPublicado() {
        when(livroRepository.existsByTituloIgnoreCase(any())).thenReturn(false);
        when(livroRepository.existsByIsbn(any())).thenReturn(false);
        when(autorRepository.findById(1)).thenReturn(Optional.of(mock(AutorEntity.class)));
        when(categoriaRepository.findById(2)).thenReturn(Optional.of(mock(CategoriaEntity.class)));
        when(livroRepository.save(any(LivroEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        bibliotecaService.adicionarLivro(livroDto);

        verify(livroRepository).save(livroCaptor.capture());
        assertThat(livroCaptor.getValue().getStatus()).isEqualTo(StatusLivro.PUBLICADO);
        assertThat(livroCaptor.getValue().getDestaque()).isFalse();
    }

    @Test
    @DisplayName("buscarLivroPorId deve falhar quando o livro não existe")
    void buscarLivroPorIdDeveFalharQuandoNaoExiste() {
        when(livroRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bibliotecaService.buscarLivroPorId(99, false))
                .isInstanceOf(LivroNaoEncontradoPorIdException.class);
    }

    @Test
    @DisplayName("buscarLivroPorId deve negar acesso a livro não publicado sem permissão")
    void buscarLivroPorIdDeveNegarAcessoANaoPublicado() {
        LivroEntity rascunho = LivroEntity.builder().status(StatusLivro.RASCUNHO).build();
        when(livroRepository.findById(1)).thenReturn(Optional.of(rascunho));

        assertThatThrownBy(() -> bibliotecaService.buscarLivroPorId(1, false))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @DisplayName("buscarLivroPorTitulo deve retornar lista vazia para título em branco")
    void buscarLivroPorTituloDeveRetornarVazioParaTituloEmBranco() {
        assertThat(bibliotecaService.buscarLivroPorTitulo("   ")).isEmpty();
        verify(livroRepository, never()).findByTituloContainingIgnoreCase(any());
    }

    @Test
    @DisplayName("editarLivro deve falhar quando o livro não existe")
    void editarLivroDeveFalharQuandoNaoExiste() {
        when(livroRepository.findById(123)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bibliotecaService.editarLivro(123, livroDto))
                .isInstanceOf(LivroNaoEncontradoPorIdException.class);
    }

    @Test
    @DisplayName("deletarLivro deve remover o livro existente")
    void deletarLivroDeveRemoverLivroExistente() {
        LivroEntity livro = LivroEntity.builder().titulo("Dom Casmurro").build();
        when(livroRepository.findById(anyInt())).thenReturn(Optional.of(livro));

        bibliotecaService.deletarLivro(1);

        verify(livroRepository).delete(livro);
    }
}
