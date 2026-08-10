console.log("Home carregado");

function criarCardLivro(livro) {

    return `

    <div class="col-xl-3 col-lg-4 col-md-6 mb-4">

        <div class="book-card h-100">

            <img
                src="assets/img/capa3.jpg"
                alt="${livro.titulo}">

            <div class="book-info">

                <span class="categoria-badge">

                    ${livro.categoria}

                </span>

                <h5>

                    ${livro.titulo}

                </h5>

                <p>

                    ${livro.autor}

                </p>

                <small>

                    ISBN:
                    ${livro.isbn}

                </small>

                <a
                    class="btn btn-book mt-3 w-100"
                    data-livro-id="${livro.id}"
                    href="ler.html?id=${encodeURIComponent(livro.id)}">

                    📖 Ler Livro

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
