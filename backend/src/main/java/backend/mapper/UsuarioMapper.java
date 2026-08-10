package backend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import backend.Entity.UsuarioEntity;
import backend.dto.Request.UsuarioDto;
import backend.dto.Response.UsuarioResponse;

@Component
public class UsuarioMapper {



    public UsuarioEntity editarEntidade(UsuarioEntity usuarioEntity, UsuarioDto usuarioDto) {
       
        usuarioEntity.setNome(usuarioDto.getNome());
        usuarioEntity.setSexo(usuarioDto.getSexo());
        usuarioEntity.setDataNascimento(usuarioDto.getDataNascimento());
        
        // Retorna a mesma entidade, mantendo o ID e a matrícula originais intactos
        return usuarioEntity;
    }

    public UsuarioResponse converterParaResponse(UsuarioEntity usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sexo(usuario.getSexo())
                .idade(usuario.getIdade())
                .dataNascimento(usuario.getDataNascimento())
                .matricula(usuario.getMatricula())
                .build();
    }

    public List<UsuarioResponse> converterParaListaDeResponse(List<UsuarioEntity> usuarios) {
        return usuarios.stream()
                .map(this::converterParaResponse)
                .toList();
    }
}
