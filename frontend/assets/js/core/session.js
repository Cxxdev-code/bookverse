const CHAVE_SESSAO = "bookverseSessao";

export function salvarSessao(autenticacao) {
    const expiraEm = Date.now() + Number(autenticacao.expiraEmSegundos || 0) * 1000;
    sessionStorage.setItem(CHAVE_SESSAO, JSON.stringify({
        token: autenticacao.token,
        usuario: autenticacao.usuario,
        expiraEm
    }));
}

export function obterSessao() {
    try {
        const sessao = JSON.parse(sessionStorage.getItem(CHAVE_SESSAO) || "null");
        if (!sessao?.token || !sessao?.usuario || !sessao.expiraEm || Date.now() >= sessao.expiraEm) {
            limparSessao();
            return null;
        }
        return sessao;
    } catch (_) {
        limparSessao();
        return null;
    }
}

export function obterToken() { return obterSessao()?.token || null; }
export function obterUsuarioAtivo() { return obterSessao()?.usuario || null; }
export function ehAdministrador() { return obterUsuarioAtivo()?.papel === "ADMIN"; }

export function atualizarUsuarioAtivo(usuario) {
    const sessao = obterSessao();
    if (!sessao) return;
    sessionStorage.setItem(CHAVE_SESSAO, JSON.stringify({ ...sessao, usuario }));
}

export function limparSessao() { sessionStorage.removeItem(CHAVE_SESSAO); }
