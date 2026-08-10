package backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import backend.Entity.StatusLivro;
import backend.dto.Request.AutorDto;
import backend.dto.Request.CategoriaDto;
import backend.dto.Request.LivroDto;
import backend.dto.Response.HomeResponse;
import backend.dto.Response.LivroCardResponse;
import backend.dto.Response.PageResponse;
import backend.service.AutorService;
import backend.service.BibliotecaService;
import backend.service.CategoriaService;
import backend.service.HomeService;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:catalogo_enriquecido_teste;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=update"
})
class CatalogoEnriquecidoIntegrationTest {

    @Autowired private AutorService autorService;
    @Autowired private CategoriaService categoriaService;
    @Autowired private BibliotecaService bibliotecaService;
    @Autowired private HomeService homeService;

    @Test
    void catalogoPublicoMostraApenasPublicadosComMetadadosEHomeComTotais() {
        var autor = autorService.criarAutor(AutorDto.builder()
                .nome("Autora de teste")
                .biografia("Biografia de teste com tamanho suficiente para a validação do cadastro.")
                .dataNascimento(LocalDate.of(1980, 1, 1))
                .nacionalidade("Brasileira")
                .build());
        var categoria = categoriaService.adicionarCategoria(CategoriaDto.builder()
                .nome("Tecnologia de teste")
                .descricao("Livros para validação")
                .build());

        bibliotecaService.adicionarLivro(livro("Livro publicado", "9780000000001", autor.getId(), categoria.getId(), StatusLivro.PUBLICADO));
        bibliotecaService.adicionarLivro(livro("Livro rascunho", "9780000000002", autor.getId(), categoria.getId(), StatusLivro.RASCUNHO));

        PageResponse<LivroCardResponse> pagina = bibliotecaService.buscarCatalogo(
                0, 12, "publicado", categoria.getId(), autor.getId(), "recentes");
        HomeResponse home = homeService.carregarHome();

        assertThat(pagina.getContent()).hasSize(1);
        assertThat(pagina.getContent().getFirst().getCapaUrl()).isEqualTo("https://exemplo.com/capa.jpg");
        assertThat(pagina.getContent().getFirst().getNumeroPaginas()).isEqualTo(320);
        assertThat(pagina.getContent().getFirst().getStatus()).isEqualTo(StatusLivro.PUBLICADO);
        assertThat(home.getTotais().getLivros()).isEqualTo(1);
        assertThat(home.getRecentes()).hasSize(1);
        assertThat(home.getCategorias().getFirst().getQuantidadeLivros()).isEqualTo(2);
        assertThat(autorService.buscarPorId(autor.getId()).getQuantidadeLivros()).isEqualTo(2);
        assertThat(categoriaService.buscarCategoriaPorId(categoria.getId()).getQuantidadeLivros()).isEqualTo(2);
    }

    private LivroDto livro(String titulo, String isbn, Integer autorId, Integer categoriaId, StatusLivro status) {
        return LivroDto.builder()
                .titulo(titulo)
                .isbn(isbn)
                .descricao("Descrição detalhada para garantir o retorno completo e o resumo usado no card.")
                .publicado(LocalDate.of(2024, 1, 1))
                .autorId(autorId)
                .categoriaId(categoriaId)
                .capaUrl("https://exemplo.com/capa.jpg")
                .numeroPaginas(320)
                .idioma("Português")
                .editora("Editora de teste")
                .edicao("1ª edição")
                .classificacaoEtaria("Livre")
                .destaque(true)
                .status(status)
                .build();
    }
}
