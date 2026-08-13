/* Executado no <head> para definir o tipo de menu antes da navbar aparecer. */
(() => {
    try {
        const sessao = JSON.parse(sessionStorage.getItem("bookverseSessao") || "null");
        const sessaoValida = sessao?.token && sessao?.usuario && sessao?.expiraEm && Date.now() < sessao.expiraEm;
        if (!sessaoValida) return;

        document.documentElement.classList.add(
            sessao.usuario.papel === "ADMIN" ? "navbar-administrador" : "navbar-usuario"
        );
    } catch (_) {
        // Uma sessão inválida será limpa e redirecionada pelo app.js.
    }
})();
