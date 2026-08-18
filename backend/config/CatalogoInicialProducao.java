package backend.config;

import java.time.LocalDate;
import java.util.List;

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
 * Carrega o acervo de demonstração somente na primeira inicialização pública.
 * Dados de contas locais nunca fazem parte dessa carga.
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
            CategoriaEntity ficcaoCientifica = categoriaRepository.save(categoria("Ficção Científica",
                    "Histórias especulativas sobre futuros, ciência, tecnologia e sociedade."));
            CategoriaEntity documentoHistorico = categoriaRepository.save(categoria("Documento histórico",
                    "Engloba registros originais que servem como testemunho direto de uma época, pessoa ou evento do passado."));
            CategoriaEntity mitologiaGrega = categoriaRepository.save(categoria("Mitologia Grega",
                    "Conjunto de mitos, lendas e narrativas criadas pelos povos da Grécia Antiga para explicar a origem do universo, os fenômenos da natureza e o comportamento humano."));
            CategoriaEntity fantasia = categoriaRepository.save(categoria("Ficção de Fantasia",
                    "A Ficção de Fantasia é um gênero literário e cinematográfico definido pela presença de elementos sobrenaturais, mágicos ou irreais que não existem no mundo real e não possuem explicação científica."));

            AutorEntity robert = autorRepository.save(autor("Robert C. Martin",
                    "Autor e engenheiro de software conhecido por defender código limpo, práticas ágeis e desenvolvimento sustentável.",
                    LocalDate.of(1952, 12, 5), "Estadunidense"));
            AutorEntity andrew = autorRepository.save(autor("Andrew Hunt",
                    "Programador e autor reconhecido por ensinar práticas pragmáticas para desenvolvimento de software profissional.",
                    LocalDate.of(1964, 1, 1), "Estadunidense"));
            autorRepository.save(autor("Frank Herbert",
                    "Autor norte-americano de ficção científica, reconhecido por construir universos políticos, ecológicos e filosóficos.",
                    LocalDate.of(1920, 10, 8), "Estadunidense"));
            AutorEntity sunTzu = autorRepository.save(autor("Sun Tzu",
                    "Foi um general e filósofo chinês. Ele ficou mundialmente famoso por escrever A Arte da Guerra, o tratado militar mais influente da história. Sua filosofia principal era vencer o inimigo pela inteligência, defendendo que a maior vitória é derrotar o oponente sem precisar lutar.",
                    LocalDate.of(500, 1, 1), "Chinesa"));
            AutorEntity homero = autorRepository.save(autor("Homero",
                    "Foi um poeta épico da Grécia Antiga, considerado o fundador da literatura ocidental e tradicionalmente apontado como o criador das obras-primas.",
                    LocalDate.of(7, 12, 29), "Turco"));
            AutorEntity lewis = autorRepository.save(autor("Lewis Carroll",
                    "O renomado escritor britânico nasceu em 27 de janeiro de 1832 e faleceu em 14 de janeiro de 1898. Seu nome verdadeiro era Charles Lutwidge Dodgson. Além de autor literário, ele atuou profissionalmente como matemático, lógico, fotógrafo e diácono anglicano na Inglaterra vitoriana.",
                    LocalDate.of(1832, 1, 28), "Britânico"));

            livroRepository.saveAll(List.of(
                    livro("9780132350884", "Clean code",
                            "Até mesmo um código ruim pode funcionar. Mas se o código não for limpo, pode levar uma organização de desenvolvimento à ruína. Todos os anos, incontáveis horas e recursos significativos são perdidos por causa de código mal escrito. Mas não precisa ser assim.\n\nO renomado especialista em software Robert C. Martin apresenta um paradigma revolucionário com Clean Code: A Handbook of Agile Software Craftsmanship (Código Limpo: Um Manual de Artesanato de Software Ágil). Martin uniu-se a seus colegas da Object Mentor para condensar suas melhores práticas ágeis de limpeza de código em tempo real em um livro que incutirá em você os valores de um artesão de software e o tornará um programador melhor — mas somente se você se dedicar a isso.",
                            tecnologia, robert, LocalDate.of(2008, 8, 1),
                            "https://m.media-amazon.com/images/I/71nj3JM-igL._SL1500_.jpg", 464, "Inglês",
                            "Prentice Hall", "1ª edição", true,
                            "https://drive.google.com/file/d/12LWzbz_I6RLo5YetmSu_mURP2Zz1vZeJ/view?usp=sharing"),
                    livro("9780135957059", "The Pragmatic Programmer",
                            "O livro The Pragmatic Programmer é um daqueles raros livros de tecnologia que você lerá, relerá e relerá ao longo dos anos. Seja você um novato na área ou um profissional experiente, sempre encontrará novas perspectivas a cada leitura.\n\nDave Thomas e Andy Hunt escreveram a primeira edição deste livro influente em 1999 para ajudar seus clientes a criar softwares melhores e redescobrir o prazer de programar. Essas lições ajudaram uma geração de programadores a examinar a própria essência do desenvolvimento de software, independentemente de qualquer linguagem, framework ou metodologia específica, e a filosofia pragmática gerou centenas de livros, screencasts e audiolivros, além de milhares de carreiras e histórias de sucesso.",
                            tecnologia, andrew, LocalDate.of(2019, 9, 13),
                            "https://m.media-amazon.com/images/I/61ztlXgCmpL._SL1500_.jpg", 352, "Inglês",
                            "Addison-Wesley", "2ª edição", true,
                            "https://drive.google.com/file/d/12nbbUCeP5jiFAbclS2lGhuwUQXgoTVXB/view?usp=sharing"),
                    livro("8593156630", "Arte da Guerra",
                            "Filósofo que se tornou general cujo nome individual era Wu, nasceu no Estado de Ch’i na China, próximo de 500 a.C., em um auge das ciências militares e legislativas daquele país. Sun Tzu escreveu a Arte da Guerra.",
                            documentoHistorico, sunTzu, LocalDate.of(2018, 8, 6),
                            "https://http2.mlstatic.com/D_NQ_NP_2X_957869-MLA87851138820_072025-F-a-arte-da-guerra-a-arte-da-guerra--capa-dura-autor-sun-tzu-genero-historia-editora-verissimo-1a-edicao-portugues-capa-dura.webp",
                            146, "Português", "Buzz Editora", "1ª edição", true,
                            "https://drive.google.com/file/d/18djvXRuIh3WKd-UmG3Kc7jGrqSaB8tAL/view?usp=sharing"),
                    livro("978-6555523560", "A Odisseia",
                            "Um dos principais poemas épicos da Grécia Antiga, a obra A Odisseia é consagrada ao retorno do rei Ulisses ou Odisseu, que durante dez anos enfrentou perigos na terra e no mar até conseguir chegar ao reino de Ítaca.\n\nHerói da Guerra de Troia, Ulisses ficou preso em uma ilha durante anos, até finalmente partir com seus doze navios e homens, em uma espetacular jornada repleta de obstáculos, para encontrar a mulher Penélope e o filho Telêmaco. A batalha contra o Ciclope, o sedutor canto das sereias e a fúria de Netuno, deus dos mares, são alguns dos episódios fabulosos dessa obra, retratados em versos ao mesmo tempo dramáticos e poéticos.",
                            mitologiaGrega, homero, LocalDate.of(2021, 6, 14),
                            "https://www.odysseus.com.br/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/a/-/a-odisseia_1.jpg",
                            398, "Português", "Penguin Companhia", "1ª edição", false,
                            "https://drive.google.com/file/d/1R9apkVAgmpzH1A2dw47FIg6u8LOPqAun/view?usp=sharing"),
                    livro("978-8594541758", "Alice no País das Maravilhas",
                            "Uma menina, um coelho e uma história capazes de fazer qualquer um de nós voltar a sonhar. Alice é despertada de um leve sono ao pé de uma árvore por um coelho peculiar. Uma criatura alva e falante com roupas engraçadas, que consulta seu relógio e reclama do próprio atraso. Curiosa como toda criança, Alice segue o animal até cair em um buraco sem fim que mudou para sempre a literatura infantil.",
                            fantasia, lewis, LocalDate.of(2019, 8, 5),
                            "https://m.media-amazon.com/images/I/81eAcV387dL._AC_UF1000,1000_QL80_.jpg", 208,
                            "Português", "Darkside Books", "1ª", false,
                            "https://drive.google.com/file/d/1iudLZsWY-qCFKVMzwEtetK2dtqqFFjXY/view?usp=sharing")));
        };
    }

    private CategoriaEntity categoria(String nome, String descricao) {
        return CategoriaEntity.builder().nome(nome).descricao(descricao).build();
    }

    private AutorEntity autor(String nome, String biografia, LocalDate nascimento, String nacionalidade) {
        return AutorEntity.builder().nome(nome).biografia(biografia).dataNascimento(nascimento)
                .nacionalidade(nacionalidade).build();
    }

    private LivroEntity livro(String isbn, String titulo, String descricao, CategoriaEntity categoria, AutorEntity autor,
            LocalDate publicacao, String capaUrl, Integer paginas, String idioma, String editora, String edicao,
            boolean destaque, String urlLeitura) {
        return LivroEntity.builder().isbn(isbn).titulo(titulo).descricao(descricao).categoria(categoria).autor(autor)
                .dataPublicacao(publicacao).capaUrl(capaUrl).numeroPaginas(paginas).idioma(idioma).editora(editora)
                .edicao(edicao).classificacaoEtaria("Livre").status(StatusLivro.PUBLICADO).destaque(destaque)
                .urlLeitura(urlLeitura).build();
    }
}
