import { limparSessao, obterToken } from "./session.js";
import { API_URL_CONFIGURADA } from "./api-config.js";

export const API_URL = API_URL_CONFIGURADA;

export class ApiError extends Error {
    constructor(message, status = 0, details = {}) {
        super(message);
        this.name = "ApiError";
        this.status = status;
        this.details = details;
    }
}

async function lerErro(resposta) {
    try { return await resposta.json(); } catch (_) { return {}; }
}

function redirecionarParaLogin() {
    if (window.location.pathname.endsWith("login.html")) return;
    const arquivo = window.location.pathname.split("/").pop() || "index.html";
    const retorno = `${arquivo}${window.location.search}`;
    window.location.href = `login.html?retorno=${encodeURIComponent(retorno)}&motivo=sessao`;
}

export async function requisicao(caminho, opcoes = {}) {
    const { method = "GET", body, publico = false } = opcoes;
    const configuracao = { method, headers: { Accept: "application/json" } };
    const token = obterToken();
    if (token && !publico) configuracao.headers.Authorization = `Bearer ${token}`;
    if (body !== undefined) {
        configuracao.headers["Content-Type"] = "application/json";
        configuracao.body = JSON.stringify(body);
    }

    let resposta;
    try {
        resposta = await fetch(`${API_URL}${caminho}`, configuracao);
    } catch (_) {
        throw new ApiError("Não foi possível conectar à API. Verifique se o backend está em execução.");
    }

    if (!resposta.ok) {
        const erro = await lerErro(resposta);
        const mensagem = erro.message || `Não foi possível concluir a operação (${resposta.status}).`;
        if (resposta.status === 401 && !publico) {
            limparSessao();
            redirecionarParaLogin();
        }
        throw new ApiError(mensagem, resposta.status, erro);
    }
    if (resposta.status === 204) return null;
    return resposta.json();
}
