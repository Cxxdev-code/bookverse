package com.cxxdev.bookverse.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.cxxdev.bookverse.entity.UsuarioEntity;
import com.cxxdev.bookverse.dto.request.AtualizarPerfilDto;
import com.cxxdev.bookverse.dto.request.UsuarioDto;
import com.cxxdev.bookverse.dto.response.UsuarioResponse;
import com.cxxdev.bookverse.exception.usuario.UsuarioIdNaoEncontradoException;
import com.cxxdev.bookverse.mapper.UsuarioMapper;
import com.cxxdev.bookverse.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Integer gerarMatricula() { return (int) usuarioRepository.countAllByMatriculaIsNotNull() + 3; }

    public List<UsuarioResponse> buscarTodosUsuarios() {
        return usuarioMapper.converterParaListaDeResponse(usuarioRepository.findAll());
    }

    /** Rota antiga mantida somente para compatibilidade administrativa. */
    public UsuarioResponse criarUsuario(UsuarioDto usuarioDto) {
        throw new UnsupportedOperationException("Use a rota /api/auth/registrar para criar contas.");
    }

    public UsuarioResponse editarUsuario(Integer id, UsuarioDto usuarioDto) {
        UsuarioEntity usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioIdNaoEncontradoException(id));
        return usuarioMapper.converterParaResponse(usuarioRepository.save(usuarioMapper.editarEntidade(usuario, usuarioDto)));
    }

    public UsuarioResponse deletarUsuario(Integer id) {
        UsuarioEntity usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioIdNaoEncontradoException(id));
        usuarioRepository.delete(usuario);
        return usuarioMapper.converterParaResponse(usuario);
    }

    public UsuarioResponse buscarUsuarioPorId(Integer id) {
        return usuarioMapper.converterParaResponse(usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioIdNaoEncontradoException(id)));
    }

    public List<UsuarioResponse> buscarUsuarioPorNome(String nome) {
        return usuarioMapper.converterParaListaDeResponse(usuarioRepository.findByNomeContainingIgnoreCase(nome));
    }

    public UsuarioResponse buscarPorEmail(String email) {
        return usuarioMapper.converterParaResponse(usuarioRepository.findByEmailIgnoreCase(normalizarEmail(email))
                .orElseThrow(() -> new UsuarioIdNaoEncontradoException(0)));
    }

    public UsuarioResponse atualizarPerfil(String email, AtualizarPerfilDto dto) {
        UsuarioEntity usuario = usuarioRepository.findByEmailIgnoreCase(normalizarEmail(email))
                .orElseThrow(() -> new UsuarioIdNaoEncontradoException(0));
        usuario.setNome(dto.getNome().trim());
        usuario.setSexo(dto.getSexo().trim());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setImagemPerfilUrl(limpar(dto.getImagemPerfilUrl()));
        return usuarioMapper.converterParaResponse(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponse> buscarHistoricoDeUsuarios() {
        return usuarioMapper.converterParaListaDeResponse(usuarioRepository.findAllByOrderByCriadoEmDesc());
    }

    private String normalizarEmail(String email) { return email.trim().toLowerCase(Locale.ROOT); }
    private String limpar(String valor) { return valor == null || valor.isBlank() ? null : valor.trim(); }
}
