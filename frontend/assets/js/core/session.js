const CHAVE_MATRICULA = "bookverseUsuarioMatricula";

export function salvarUsuarioAtivo(usuario) {
    sessionStorage.setItem(CHAVE_MATRICULA, String(usuario.matricula));
}

export function obterMatriculaAtiva() {
    return sessionStorage.getItem(CHAVE_MATRICULA);
}

export function limparUsuarioAtivo() {
    sessionStorage.removeItem(CHAVE_MATRICULA);
}
