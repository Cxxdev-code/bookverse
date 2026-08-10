let modoAutor = "existente";
let modoCategoria = "existente";

function porId(id) {
    return document.getElementById(id);
}

function definirObrigatoriedade(seletor, obrigatorio) {
    document.querySelectorAll(seletor).forEach(campo => {
        campo.required = obrigatorio;
        campo.disabled = !obrigatorio;
    });
}

function exibirMensagem(tipo, texto) {
    const mensagem = porId("mensagemFormulario");
    mensagem.className = `form-message ${tipo}`;
    mensagem.innerHTML = `<i class="bi bi-${tipo === "success" ? "check-circle-fill" : "exclamation-triangle-fill"}"></i><span>${texto}</span>`;
    mensagem.scrollIntoView({ behavior: "smooth", block: "center" });
}

function configurarModo(tipo, modo) {
    const eAutor = tipo === "autor";
    const existente = porId(`${tipo}Existente`);
    const novo = porId(`${tipo}Novo`);
    const select = porId(eAutor ? "autorId" : "categoriaId");

    if (eAutor) modoAutor = modo;
    else modoCategoria = modo;

    document.querySelectorAll(`[data-tipo="${tipo}"]`).forEach(botao => {
        botao.classList.toggle("active", botao.dataset.modo === modo);
    });

    const usarNovo = modo === "novo";
    existente.classList.toggle("d-none", usarNovo);
    novo.classList.toggle("d-none", !usarNovo);
    select.required = !usarNovo;
    select.disabled = usarNovo;
    definirObrigatoriedade(eAutor ? ".novo-autor" : ".nova-categoria", usarNovo);
    atualizarEtapas();
}

function preencherSelect(id, itens, textoPadrao) {
    const select = porId(id);
    select.innerHTML = `<option value="">${textoPadrao}</option>` + itens
        .map(item => `<option value="${item.id}">${item.nome}</option>`)
        .join("");
}

function campoPreenchido(id) {
    return porId(id).value.trim().length > 0;
}

function livroPreenchido() {
    return campoPreenchido("titulo") &&
        campoPreenchido("isbn") &&
        campoPreenchido("publicado") &&
        campoPreenchido("descricao");
}

function autorPreenchido() {
    if (modoAutor === "existente") return campoPreenchido("autorId");

    return campoPreenchido("nomeAutor") &&
        campoPreenchido("nascimentoAutor") &&
        campoPreenchido("nacionalidadeAutor") &&
        porId("biografiaAutor").value.trim().length >= 30;
}

function categoriaPreenchida() {
    if (modoCategoria === "existente") return campoPreenchido("categoriaId");

    return campoPreenchido("nomeCategoria") && campoPreenchido("descricaoCategoria");
}

function atualizarEtapas() {
    const livroPronto = livroPreenchido();
    const autorPronto = autorPreenchido();
    const categoriaPronta = categoriaPreenchida();
    const etapas = {
        livro: porId("formAdicionarLivro")?.querySelector('[data-etapa="livro"]'),
        autor: porId("formAdicionarLivro")?.querySelector('[data-etapa="autor"]'),
        categoria: porId("formAdicionarLivro")?.querySelector('[data-etapa="categoria"]')
    };

    if (!etapas.livro) return;

    etapas.livro.classList.toggle("active", !livroPronto);
    etapas.livro.classList.toggle("completed", livroPronto);
    etapas.autor.classList.toggle("active", livroPronto && !autorPronto);
    etapas.autor.classList.toggle("completed", livroPronto && autorPronto);
    etapas.categoria.classList.toggle("active", livroPronto && autorPronto && !categoriaPronta);
    etapas.categoria.classList.toggle("completed", livroPronto && autorPronto && categoriaPronta);
    porId("formAdicionarLivro").querySelector('[data-linha="autor"]')
        .classList.toggle("completed", livroPronto);
    porId("formAdicionarLivro").querySelector('[data-linha="categoria"]')
        .classList.toggle("completed", livroPronto && autorPronto);
}

async function requisitar(url, corpo) {
    const resposta = await fetch(`${API_URL}${url}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(corpo)
    });

    if (!resposta.ok) {
        let erro = {};
        try { erro = await resposta.json(); } catch (_) { /* resposta sem JSON */ }
        throw new Error(erro.message || "Não foi possível concluir o cadastro.");
    }

    return resposta.json();
}

async function criarAutorSeNecessario() {
    if (modoAutor === "existente") return Number(porId("autorId").value);

    const autor = await requisitar("/autores", {
        nome: porId("nomeAutor").value.trim(),
        dataNascimento: porId("nascimentoAutor").value,
        nacionalidade: porId("nacionalidadeAutor").value.trim(),
        biografia: porId("biografiaAutor").value.trim()
    });

    return autor.id;
}

async function criarCategoriaSeNecessario() {
    if (modoCategoria === "existente") return Number(porId("categoriaId").value);

    const categoria = await requisitar("/categorias", {
        nome: porId("nomeCategoria").value.trim(),
        descricao: porId("descricaoCategoria").value.trim()
    });

    return categoria.id;
}

async function atualizarRelacoes() {
    const [autores, categorias] = await Promise.all([buscarAutores(), buscarCategorias()]);
    preencherSelect("autorId", autores, "Selecione um autor");
    preencherSelect("categoriaId", categorias, "Selecione uma categoria");
    atualizarEtapas();
}

function restaurarFormulario() {
    porId("formAdicionarLivro").reset();
    configurarModo("autor", "existente");
    configurarModo("categoria", "existente");
}

async function salvarLivro(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    const botao = porId("btnSalvarLivro");

    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        exibirMensagem("error", "Preencha todos os campos obrigatórios antes de salvar.");
        return;
    }

    botao.disabled = true;
    botao.innerHTML = '<span class="spinner-border spinner-border-sm" aria-hidden="true"></span> Salvando...';

    try {
        const [autorId, categoriaId] = await Promise.all([
            criarAutorSeNecessario(),
            criarCategoriaSeNecessario()
        ]);

        await requisitar("/livros", {
            titulo: porId("titulo").value.trim(),
            isbn: porId("isbn").value.trim(),
            publicado: porId("publicado").value,
            descricao: porId("descricao").value.trim(),
            autorId,
            categoriaId
        });

        await atualizarRelacoes();
        restaurarFormulario();
        formulario.classList.remove("was-validated");
        exibirMensagem("success", "Livro cadastrado com sucesso! Ele já está disponível na Biblioteca.");
    } catch (erro) {
        console.error("Erro ao salvar livro:", erro);
        exibirMensagem("error", erro.message);
    } finally {
        botao.disabled = false;
        botao.innerHTML = '<i class="bi bi-cloud-arrow-up-fill"></i> Salvar livro';
    }
}

async function iniciarPagina() {
    try {
        await atualizarRelacoes();
    } catch (erro) {
        console.error("Erro ao carregar autores e categorias:", erro);
        exibirMensagem("error", "Não foi possível carregar autores e categorias. Verifique se a API está em execução.");
    }

    configurarModo("autor", "existente");
    configurarModo("categoria", "existente");
    document.querySelectorAll(".choice-button").forEach(botao => {
        botao.addEventListener("click", () => configurarModo(botao.dataset.tipo, botao.dataset.modo));
    });
    document.querySelectorAll(".form-book").forEach(campo => {
        campo.addEventListener("input", atualizarEtapas);
        campo.addEventListener("change", atualizarEtapas);
    });
    porId("formAdicionarLivro").addEventListener("submit", salvarLivro);
    atualizarEtapas();
}

document.addEventListener("DOMContentLoaded", iniciarPagina);
