package backend.service;
import java.util.List;

import org.springframework.stereotype.Service;

import backend.Entity.UsuarioEntity;
import backend.dto.Request.UsuarioDto;
import backend.dto.Response.UsuarioResponse;
import backend.exception.usuario.UsuarioIdNaoEncontradoException;
import backend.mapper.UsuarioMapper;
import backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Integer gerarMatricula() {
        Integer matricula = (int) (usuarioRepository.countAllByMatriculaIsNotNull() + 3);
        return matricula;
    }

    public List<UsuarioResponse> buscarTodosUsuarios(){
        List<UsuarioEntity> listaUsuarioEntity = usuarioRepository.findAll();

        List<UsuarioResponse> listaUsuarioResponse = usuarioMapper.converterParaListaDeResponse(listaUsuarioEntity);

        return listaUsuarioResponse;
    }

    public UsuarioResponse criarUsuario(UsuarioDto usuarioDto){
        
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome(usuarioDto.getNome())
                .sexo(usuarioDto.getSexo())
                .matricula(gerarMatricula()) // Matrícula sendo gerada automaticamente
                .dataNascimento(usuarioDto.getDataNascimento())
                .build();
        
        return usuarioMapper.converterParaResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse editarUsuario(Integer id, UsuarioDto usuarioDto){

        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioIdNaoEncontradoException(id));

        UsuarioEntity usuarioEditado = usuarioMapper.editarEntidade(usuarioEntity, usuarioDto);

        usuarioRepository.save(usuarioEditado);

        return usuarioMapper.converterParaResponse(usuarioEditado);
    }

    public UsuarioResponse deletarUsuario(Integer id){
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioIdNaoEncontradoException(id));

        usuarioRepository.delete(usuarioEntity);

        UsuarioResponse usuarioDeletado = usuarioMapper.converterParaResponse(usuarioEntity);

        return usuarioDeletado;
    }
    
    public UsuarioResponse buscarUsuarioPorId(Integer id) {

        UsuarioEntity usuarioRecebido = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioIdNaoEncontradoException(id));

        return usuarioMapper.converterParaResponse(usuarioRecebido);
    }

    public List<UsuarioResponse> buscarUsuarioPorNome(String nome) {

        List<UsuarioEntity> listaEntityUsuario = usuarioRepository.findByNomeContainingIgnoreCase(nome);

        List<UsuarioResponse> listaResponseUsario = usuarioMapper.converterParaListaDeResponse(listaEntityUsuario);

        return listaResponseUsario;
    }
}