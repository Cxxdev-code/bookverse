console.log("Home carregado");

function criarCardLivro(livro) {

    return `

        <div class="col-lg-3 col-md-6 mb-4">

            <div class="book-card">

                <img src="assets/img/baner.png"
                     alt="${livro.titulo}">

                <div class="book-info">

                    <h5>${livro.titulo}</h5>

                    <span>${livro.autor}</span>

                    <small>${livro.categoria}</small>

                    <a href="#"
                       class="btn btn-book w-100 mt-3">

                        Ler

                    </a>

                </div>

            </div>

        </div>

    `;

}

async function carregarLivros() {

    const livros = await buscarLivros();

    console.log(livros);

    const listaLivros = document.getElementById("listaLivros");

    listaLivros.innerHTML = "";

    livros.forEach(livro => {

        listaLivros.innerHTML += criarCardLivro(livro);

    });

}

carregarLivros();