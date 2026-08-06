const API_URL = "http://localhost:8080/api";

async function buscarLivros() {

    try {

        const resposta = await fetch(`${API_URL}/livros/todos`);

        if (!resposta.ok) {
            throw new Error("Erro ao buscar livros.");
        }

        return await resposta.json();

    } catch (erro) {

        console.error(erro);

        return [];

    }

}