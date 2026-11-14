import { iniciarHome } from "./controllers/home-controller.js?v=deploy-20261114";
import { iniciarLivros } from "./controllers/livros-controller.js?v=deploy-20261114";
import { iniciarAutores } from "./controllers/autores-controller.js?v=deploy-20261114";
import { iniciarCategorias } from "./controllers/categorias-controller.js?v=deploy-20261114";
import { iniciarAdicionar } from "./controllers/adicionar-controller.js?v=deploy-20261114";
import { iniciarLogin } from "./controllers/login-controller.js?v=deploy-20261114";
import { iniciarPerfil } from "./controllers/perfil-controller.js?v=deploy-20261114";
import { iniciarLeitura } from "./controllers/leitura-controller.js?v=deploy-20261114";
import { iniciarHistorico } from "./controllers/historico-controller.js?v=deploy-20261114";
import { iniciarNavegacao } from "./core/navegacao.js?v=deploy-20261114";
import { ehAdministrador, obterUsuarioAtivo } from "./core/session.js?v=deploy-20261114";

const controladores = {
    home: iniciarHome, livros: iniciarLivros, autores: iniciarAutores, categorias: iniciarCategorias,
    adicionar: iniciarAdicionar, login: iniciarLogin, perfil: iniciarPerfil, leitura: iniciarLeitura,
    historico: iniciarHistorico
};

const pagina = document.body.dataset.pagina;
const paginasProtegidas = new Set(["home", "livros", "autores", "categorias", "perfil", "leitura", "adicionar", "historico"]);
const paginasAdmin = new Set(["adicionar", "historico"]);
const usuario = obterUsuarioAtivo();

if (paginasProtegidas.has(pagina) && !usuario) {
    const retorno = `${window.location.pathname.split("/").pop() || "index.html"}${window.location.search}`;
    window.location.replace(`login.html?retorno=${encodeURIComponent(retorno)}`);
} else if (paginasAdmin.has(pagina) && !ehAdministrador()) {
    window.location.replace("index.html?acesso=negado");
} else {
    iniciarNavegacao(pagina);
    const iniciar = controladores[pagina];
    if (iniciar) iniciar().catch(erro => console.error("Falha ao iniciar a página:", erro));
}
