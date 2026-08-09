const API_URL = "http://localhost:8080/api";

async function buscarLivros() {

    try {

        const resposta = await fetch(`${API_URL}/livros/todos`);

        if (!resposta.ok) {
            throw new Error(`Erro ${resposta.status}`);
        }

        const livros = await resposta.json();

        console.table(livros);

        return livros;

    } catch (erro) {

        console.error("Erro ao buscar livros:", erro);

        return [];

    }

}

async function buscarAutores() {
    try {
        const resposta = await fetch(`${API_URL}/autores/todos`);
        if (!resposta.ok) throw new Error(`Erro ${resposta.status}`);
        return await resposta.json();
    } catch (erro) {
        console.error("Erro ao buscar autores:", erro);
        return [];
    }
}

async function buscarCategorias() {
    try {
        const resposta = await fetch(`${API_URL}/categorias`);
        if (!resposta.ok) throw new Error(`Erro ${resposta.status}`);
        return await resposta.json();
    } catch (erro) {
        console.error("Erro ao buscar categorias:", erro);
        return [];
    }
}

async function buscarUsuarios() {
    try {
        const resposta = await fetch(`${API_URL}/usuarios`);
        if (!resposta.ok) throw new Error(`Erro ${resposta.status}`);
        return await resposta.json();
    } catch (erro) {
        console.error("Erro ao buscar usuários:", erro);
        return [];
    }
}
