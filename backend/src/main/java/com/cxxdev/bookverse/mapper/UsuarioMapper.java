package com.cxxdev.bookverse.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cxxdev.bookverse.entity.UsuarioEntity;
import com.cxxdev.bookverse.dto.request.UsuarioDto;
import com.cxxdev.bookverse.dto.response.UsuarioResponse;

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
                .email(usuario.getEmail())
                .papel(usuario.getPapel())
                .ativo(usuario.getAtivo())
                .imagemPerfilUrl(usuario.getImagemPerfilUrl())
                .criadoEm(usuario.getCriadoEm())
                .ultimoAcessoEm(usuario.getUltimoAcessoEm())
                .build();
    }

    public List<UsuarioResponse> converterParaListaDeResponse(List<UsuarioEntity> usuarios) {
        return usuarios.stream()
                .map(this::converterParaResponse)
                .toList();
    }
}
