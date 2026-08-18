package backend.config;

import java.time.LocalDate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import backend.Entity.AutorEntity;
import backend.Entity.CategoriaEntity;
import backend.Entity.LivroEntity;
import backend.Entity.StatusLivro;
import backend.repository.AutorRepository;
import backend.repository.CategoriaRepository;
import backend.repository.LivroRepository;

/**
 * Carrega o catálogo de demonstração somente na primeira inicialização da
 * produção. Usuários, senhas e dados pessoais nunca são copiados do ambiente
 * local para o ambiente publicado.
 */
@Configuration
@Profile("prod")
public class CatalogoInicialProducao {

    @Bean
    ApplicationRunner carregarCatalogoInicial(LivroRepository livroRepository, AutorRepository autorRepository,
            CategoriaRepository categoriaRepository) {
        return argumentos -> {
            if (livroRepository.count() > 0) return;

            CategoriaEntity tecnologia = categoriaRepository.save(categoria("Tecnologia",
                    "Livros sobre programação, arquitetura de software e inovação digital."));
            CategoriaEntity documentoHistorico = categoriaRepository.save(categoria("Documento Histórico",
                    "Obras e registros que ajudam a compreender sociedades, fatos e pensamentos do passado."));
            CategoriaEntity mitologia = categoriaRepository.save(categoria("Mitologia Grega",
                    "Mitos, lendas e narrativas da Grécia Antiga."));
            CategoriaEntity fantasia = categoriaRepository.save(categoria("Ficção de Fantasia",
                    "Histórias guiadas por imaginação, aventura e elementos extraordinários."));

            AutorEntity robert = autorRepository.save(autor("Robert C. Martin",
                    "Autor e engenheiro de software conhecido por defender código limpo, práticas ágeis e desenvolvimento sustentável.",
                    LocalDate.of(1952, 12, 5), "Estadunidense"));
            AutorEntity andrew = autorRepository.save(autor("Andrew Hunt",
                    "Programador e autor reconhecido por ensinar práticas pragmáticas para desenvolvimento de software profissional.",
                    LocalDate.of(1964, 1, 1), "Estadunidense"));
            AutorEntity sunTzu = autorRepository.save(autor("Sun Tzu",
                    "General e filósofo chinês, mundialmente conhecido pela obra A Arte da Guerra.",
                    LocalDate.of(500, 1, 1), "Chinesa"));
            AutorEntity homero = autorRepository.save(autor("Homero",
                    "Poeta épico da Grécia Antiga, tradicionalmente considerado um dos fundadores da literatura ocidental.",
                    LocalDate.of(7, 12, 29), "Grego"));
            AutorEntity lewis = autorRepository.save(autor("Lewis Carroll",
                    "Escritor britânico, criador de narrativas imaginativas que atravessam gerações.",
                    LocalDate.of(1832, 1, 27), "Britânico"));

            livroRepository.save(livro("9780132350884", "Clean Code",
                    "Um guia sobre princípios, padrões e práticas para escrever código legível, simples e sustentável.",
                    LocalDate.of(2008, 8, 1), tecnologia, robert,
                    "https://m.media-amazon.com/images/I/71nj3JM-igL._SL1500_.jpg",
                    "https://drive.google.com/file/d/12LWzbz_I6RLo5YetmSu_mURP2Zz1vZeJ/view?usp=sharing",
                    464, "Inglês", "Prentice Hall", "1ª edição", true));
            livroRepository.save(livro("9780135957059", "The Pragmatic Programmer",
                    "Um clássico sobre mentalidade, ferramentas e hábitos para evoluir continuamente como desenvolvedor.",
                    LocalDate.of(2019, 9, 13), tecnologia, andrew,
                    "https://m.media-amazon.com/images/I/61ztlXgCmpL._SL1500_.jpg",
                    "https://drive.google.com/file/d/12nbbUCeP5jiFAbclS2lGhuwUQXgoTVXB/view?usp=sharing",
                    352, "Inglês", "Addison-Wesley", "2ª edição", true));
            livroRepository.save(livro("8593156630", "Arte da Guerra",
                    "Um tratado clássico sobre estratégia, liderança e tomada de decisão em cenários de conflito.",
                    LocalDate.of(2018, 8, 6), documentoHistorico, sunTzu,
                    "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1558405815i/44254181.jpg",
                    "https://drive.google.com/file/d/18djvXRuIh3WKd-UmG3Kc7jGrqSaB8tAL/view?usp=sharing",
                    146, "Português", "Buzz Editora", "1ª edição", true));
            livroRepository.save(livro("978-6555523560", "A Odisseia",
                    "Poema épico sobre a longa viagem de retorno de Odisseu após a Guerra de Troia.",
                    LocalDate.of(2021, 6, 14), mitologia, homero,
                    "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1601350472i/55455680.jpg",
                    "https://drive.google.com/file/d/1R9apkVAgmpzH1A2dw47FIg6u8LOPqAun/view?usp=sharing",
                    398, "Português", "Penguin Companhia", "1ª edição", false));
            livroRepository.save(livro("978-8594541758", "Alice no País das Maravilhas",
                    "Uma jornada fantástica que começa ao seguir um coelho e leva Alice a um mundo de imaginação.",
                    LocalDate.of(2019, 8, 5), fantasia, lewis,
                    "https://m.media-amazon.com/images/I/81eAcV387dL._AC_UF1000,1000_QL80_.jpg",
                    "https://drive.google.com/file/d/1iudLZsWY-qCFKVMzwEtetK2dtqqFFjXY/view?usp=sharing",
                    208, "Português", "Darkside Books", "1ª edição", false));
        };
    }

    private CategoriaEntity categoria(String nome, String descricao) {
        return CategoriaEntity.builder().nome(nome).descricao(descricao).build();
    }

    private AutorEntity autor(String nome, String biografia, LocalDate dataNascimento, String nacionalidade) {
        return AutorEntity.builder().nome(nome).biografia(biografia).dataNascimento(dataNascimento)
                .nacionalidade(nacionalidade).build();
    }

    private LivroEntity livro(String isbn, String titulo, String descricao, LocalDate dataPublicacao,
            CategoriaEntity categoria, AutorEntity autor, String capaUrl, String urlLeitura, int numeroPaginas,
            String idioma, String editora, String edicao, boolean destaque) {
        return LivroEntity.builder().isbn(isbn).titulo(titulo).descricao(descricao).dataPublicacao(dataPublicacao)
                .categoria(categoria).autor(autor).capaUrl(capaUrl).urlLeitura(urlLeitura)
                .numeroPaginas(numeroPaginas).idioma(idioma).editora(editora).edicao(edicao)
                .classificacaoEtaria("Livre").status(StatusLivro.PUBLICADO).destaque(destaque).build();
    }
}
