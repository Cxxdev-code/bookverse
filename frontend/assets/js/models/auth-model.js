import { requisicao } from "../core/api-client.js?v=production-3";

export const AuthModel = {
    entrar: credenciais => requisicao("/auth/login", { method: "POST", body: credenciais, publico: true }),
    registrar: dados => requisicao("/auth/registrar", { method: "POST", body: dados, publico: true })
};
