export const API_URL = "http://localhost:8080/api";

export class ApiError extends Error {
    constructor(message, status = 0, details = {}) {
        super(message);
        this.name = "ApiError";
        this.status = status;
        this.details = details;
    }
}

async function lerErro(resposta) {
    try {
        return await resposta.json();
    } catch (_) {
        return {};
    }
}

export async function requisicao(caminho, opcoes = {}) {
    const { method = "GET", body } = opcoes;
    const configuracao = {
        method,
        headers: { Accept: "application/json" }
    };

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
        throw new ApiError(
            erro.message || `Não foi possível concluir a operação (${resposta.status}).`,
            resposta.status,
            erro
        );
    }

    if (resposta.status === 204) return null;
    return resposta.json();
}
