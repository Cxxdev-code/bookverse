import { iniciarHome } from "./controllers/home-controller.js?v=catalogo-api-1";
import { iniciarLivros } from "./controllers/livros-controller.js?v=catalogo-api-1";
import { iniciarAutores } from "./controllers/autores-controller.js";
import { iniciarCategorias } from "./controllers/categorias-controller.js";
import { iniciarAdicionar } from "./controllers/adicionar-controller.js";
import { iniciarLogin } from "./controllers/login-controller.js";
import { iniciarPerfil } from "./controllers/perfil-controller.js";
import { iniciarLeitura } from "./controllers/leitura-controller.js";
import { iniciarNavegacao } from "./core/navegacao.js";

const controladores = {
    home: iniciarHome,
    livros: iniciarLivros,
    autores: iniciarAutores,
    categorias: iniciarCategorias,
    adicionar: iniciarAdicionar,
    login: iniciarLogin,
    perfil: iniciarPerfil,
    leitura: iniciarLeitura
};

const pagina = document.body.dataset.pagina;
const iniciar = controladores[pagina];

iniciarNavegacao(pagina);

if (iniciar) {
    iniciar().catch(erro => console.error("Falha ao iniciar a página:", erro));
} else {
    console.warn("Página sem controlador MVC configurado:", pagina);
}
