import { requisicao } from "../core/api-client.js?v=deploy-20261114-2";

export const AuthModel = {
    entrar: credenciais => requisicao("/auth/login", { method: "POST", body: credenciais, publico: true }),
    registrar: dados => requisicao("/auth/registrar", { method: "POST", body: dados, publico: true })
};
