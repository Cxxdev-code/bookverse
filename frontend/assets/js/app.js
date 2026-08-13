import { iniciarHome } from "./controllers/home-controller.js?v=security-1";
import { iniciarLivros } from "./controllers/livros-controller.js?v=security-1";
import { iniciarAutores } from "./controllers/autores-controller.js";
import { iniciarCategorias } from "./controllers/categorias-controller.js";
import { iniciarAdicionar } from "./controllers/adicionar-controller.js?v=security-1";
import { iniciarLogin } from "./controllers/login-controller.js?v=security-1";
import { iniciarPerfil } from "./controllers/perfil-controller.js?v=security-1";
import { iniciarLeitura } from "./controllers/leitura-controller.js";
import { iniciarHistorico } from "./controllers/historico-controller.js";
import { iniciarNavegacao } from "./core/navegacao.js";
import { ehAdministrador, obterUsuarioAtivo } from "./core/session.js";

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
