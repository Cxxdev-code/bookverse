/* ==================================================
   AUTORES.JS
   Busca os autores na API e monta os cards na tela.
   Segue o mesmo padrão usado em livros.js.
================================================== */

// Recebe um autor (objeto vindo da API) e devolve o HTML do card.
function criarCardAutor(autor) {

    // A biografia pode ser longa (o backend aceita até 500 caracteres).
    // Corta em 120 caracteres pra não estourar o card, e só adiciona
    // "..." se realmente cortou algo.
    const bioCurta = autor.biografia && autor.biografia.length > 120
        ? autor.biografia.slice(0, 120).trim() + "..."
        : (autor.biografia || "Biografia não informada.");

    // dataNascimento vem do backend como string "AAAA-MM-DD" (formato ISO,
    // padrão do LocalDate em Java). new Date() entende esse formato direto,
    // e toLocaleDateString("pt-BR") converte pra "DD/MM/AAAA" pra exibição.
    const nascimentoFormatado = autor.dataNascimento
        ? new Date(autor.dataNascimento).toLocaleDateString("pt-BR", { timeZone: "UTC" })
        : "Data não informada";

    return `
    <div class="col-xl-3 col-lg-4 col-md-6 mb-4">
        <div class="book-card h-100">
            <div class="author-avatar">
                <i class="bi bi-person-circle"></i>
            </div>
            <div class="book-info p-3">
                <h5 class="text-white fw-bold mb-1">${autor.nome}</h5>
                <span>${autor.nacionalidade || "Nacionalidade não informada"}</span>
                <p class="text-secondary small mb-2">${bioCurta}</p>
                <small class="d-block text-secondary">
                    Nascimento: ${nascimentoFormatado}
                </small>
            </div>
        </div>
    </div>
    `;
}

// Orquestra o carregamento: busca na API, atualiza contadores,
// decide entre mostrar cards, "carregando" ou "nenhum autor".
async function carregarAutores() {

    const listaAutores = document.getElementById("listaAutores");
    const carregando = document.getElementById("carregandoAutores");
    const nenhumAutor = document.getElementById("nenhumAutor");
    const quantidadeAutores = document.getElementById("quantidadeAutores");
    const quantidadeAutoresLista = document.getElementById("quantidadeAutoresLista");

    try {
        const autores = await buscarAutores();

        // Terminou de carregar: some com o "Carregando autores..."
        carregando.classList.add("d-none");

        // Atualiza os contadores (cabeçalho e barra de resultados)
        quantidadeAutores.textContent = autores.length;
        quantidadeAutoresLista.textContent = autores.length;

        if (autores.length === 0) {
            nenhumAutor.classList.remove("d-none");
            return;
        }

        listaAutores.innerHTML = autores.map(criarCardAutor).join("");

    } catch (erro) {
        console.error("Erro ao carregar autores:", erro);
        carregando.classList.add("d-none");
        listaAutores.innerHTML = `
            <div class="col-12 text-center py-5">
                <h4 class="text-danger">Erro ao carregar os autores.</h4>
            </div>
        `;
    }
}

document.addEventListener("DOMContentLoaded", carregarAutores);