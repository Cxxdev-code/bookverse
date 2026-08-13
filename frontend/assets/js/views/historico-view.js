import { escaparHtml, porId } from "../core/dom.js";

function dataHora(valor, fallback = "Ainda não acessou") {
    if (!valor) return fallback;
    const data = new Date(valor);
    return Number.isNaN(data.getTime()) ? fallback : data.toLocaleString("pt-BR", { dateStyle: "short", timeStyle: "short" });
}

function avatar(usuario) {
    const imagem = String(usuario.imagemPerfilUrl || "").trim();
    if (/^(https?:\/\/|assets\/)/i.test(imagem)) {
        return `<img src="${escaparHtml(imagem)}" alt="Foto de ${escaparHtml(usuario.nome)}">`;
    }
    return `<span>${escaparHtml((usuario.nome || "U").trim().charAt(0).toUpperCase())}</span>`;
}

export function renderizarHistoricoUsuarios(usuarios) {
    const lista = porId("listaHistoricoUsuarios");
    if (!lista) return;
    porId("quantidadeUsuarios").textContent = usuarios.length;
    if (!usuarios.length) {
        lista.innerHTML = '<div class="history-empty"><i class="bi bi-people"></i><h2>Nenhum usuário registrado</h2><p>As novas contas aparecerão aqui.</p></div>';
        return;
    }
    lista.innerHTML = usuarios.map(usuario => `<article class="history-user-card">
        <div class="history-avatar">${avatar(usuario)}</div>
        <div class="history-user-main"><div class="history-user-title"><div><h2>${escaparHtml(usuario.nome)}</h2><p>${escaparHtml(usuario.email || "E-mail não informado")}</p></div><span class="history-role ${usuario.papel === "ADMIN" ? "admin" : "user"}">${usuario.papel === "ADMIN" ? "Administrador" : "Usuário"}</span></div>
        <div class="history-data"><span><i class="bi bi-hash"></i> Matrícula ${escaparHtml(usuario.matricula || "—")}</span><span><i class="bi bi-calendar-plus"></i> Cadastro: ${escaparHtml(dataHora(usuario.criadoEm, "Data não disponível"))}</span><span><i class="bi bi-clock-history"></i> Último acesso: ${escaparHtml(dataHora(usuario.ultimoAcessoEm))}</span></div></div>
        <span class="history-active ${usuario.ativo ? "on" : "off"}"><i class="bi bi-circle-fill"></i> ${usuario.ativo ? "Ativo" : "Inativo"}</span>
    </article>`).join("");
}
