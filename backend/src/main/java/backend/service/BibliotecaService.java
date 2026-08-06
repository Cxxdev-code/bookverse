package backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.Entity.AutorEntity;
import backend.Entity.CategoriaEntity;
import backend.Entity.LivroEntity;
import backend.dto.Request.LivroDto;
import backend.dto.Response.LivroResponse;
import backend.exception.categoria.CategoriaNaoEncontradaException;
import backend.exception.livro.LivroJaExistenteException;
import backend.exception.livro.LivroNaoEncontradoPorIdExcption;
import backend.mapper.LivroMapper;
import backend.repository.AutorRepository;
import backend.repository.CategoriaRepository;
import backend.repository.LivroRepository;
import lombok.AllArgsConstructor;



@AllArgsConstructor
@Service
public class BibliotecaService {
    
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroMapper livroMapper;

    public List<LivroResponse> buscarTodosOsLivros() {
        List<LivroEntity> listaDeLivros = livroRepository.findAll();
        return livroMapper.converterParaListaDeResponse(listaDeLivros);
    }

    public LivroResponse adicionarLivro(LivroDto livroDto) {
        String titulo = livroDto.getTitulo();
        if (livroRepository.existsByTitulo(titulo)) {
            throw new LivroJaExistenteException("Livro Já cadastrado com Titulo: " + titulo);   
        }

        // 1. Buscar Autor e Categoria pelos IDs fornecidos
        AutorEntity autor = autorRepository.findById(livroDto.getAutorId())
            .orElseThrow(() -> new RuntimeException( "Autor não encontrado com ID: " + livroDto.getAutorId())); // Troque por AutorNaoEncontradoException
            
        CategoriaEntity categoria = categoriaRepository.findById(livroDto.getCategoriaId())
            .orElseThrow(() -> new CategoriaNaoEncontradaException(livroDto.getCategoriaId()));

        // 2. Montar a entidade de Livro utilizando os objetos encontrados no banco
        LivroEntity livroAdicionado = LivroEntity.builder()
            .titulo(livroDto.getTitulo())
            .isbn(livroDto.getIsbn())
            .descricao(livroDto.getDescricao())
            .dataPublicacao(livroDto.getPublicado())
            .autor(autor)         // Atribui a entidade Autor completa
            .categoria(categoria) // Atribui a entidade Categoria completa
            .build();

        livroRepository.save(livroAdicionado);
            
        LivroResponse response = livroMapper.converterParaResponse(livroAdicionado);
        response.setAutor(autor.getNome());
        response.setTitulo(livroDto.getTitulo());


        return response;
    }  

    public LivroResponse buscarLivroPorId(Integer id) {
        LivroEntity entidadeLivroRecebida = livroRepository.findById(id)
            .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        return livroMapper.converterParaResponse(entidadeLivroRecebida);
    }

    public List<LivroResponse> buscarLivroPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return List.of(); 
        }
        List<LivroEntity> livrosEncontrados = livroRepository.findByTituloContainingIgnoreCase(titulo);
        return livroMapper.converterParaListaDeResponse(livrosEncontrados);
    }

    public LivroResponse editarLivro(Integer id, LivroDto livroDto) {
        LivroEntity entidadeLivroRecebida = livroRepository.findById(id)
            .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        
        AutorEntity autor = autorRepository.findById(livroDto.getAutorId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + livroDto.getAutorId()));
            
        CategoriaEntity categoria = categoriaRepository.findById(livroDto.getCategoriaId())
            .orElseThrow(() -> new CategoriaNaoEncontradaException(livroDto.getCategoriaId()));
        
        entidadeLivroRecebida.setTitulo(livroDto.getTitulo());
        entidadeLivroRecebida.setDescricao(livroDto.getDescricao());
        entidadeLivroRecebida.setDataPublicacao(livroDto.getPublicado());
        entidadeLivroRecebida.setAutor(autor);
        entidadeLivroRecebida.setCategoria(categoria);

        livroRepository.save(entidadeLivroRecebida);

        return livroMapper.converterParaResponse(entidadeLivroRecebida);
    }

    public LivroResponse deletarLivro(Integer id) {
        LivroEntity entidadeLivroRecebida = livroRepository.findById(id)
            .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        
        livroRepository.delete(entidadeLivroRecebida);

        return livroMapper.converterParaResponse(entidadeLivroRecebida);
    }
}
