# Rotas e telas

## Convenções

- A URL base do front-end é configurada em `frontend/assets/js/core/api-client.js`.
- Datas usam ISO `YYYY-MM-DD` nas requisições e são formatadas como `DD/MM/AAAA` nas telas.
- Falhas HTTP são lançadas como `ApiError`, com status, mensagem e detalhes retornados pela API.

## Livros

| Método | Rota | Modelo | Uso na interface |
|---|---|---|---|
| GET | `/api/livros/todos` | `LivroModel.listar` | Home e Biblioteca |
| GET | `/api/livros/{id}` | `LivroModel.buscarPorId` | Modal de detalhes e página `ler.html?id={id}` |
| GET | `/api/livros?titulo=` | `LivroModel.buscarPorTitulo` | Disponível para pesquisa remota |
| POST | `/api/livros` | `LivroModel.criar` | Página Adicionar |
| PUT | `/api/livros/{id}` | `LivroModel.atualizar` | Contrato disponível para edição |
| DELETE | `/api/livros/{id}` | `LivroModel.remover` | Contrato disponível para remoção |

`LivroResponse` contém `id`, título, descrição, ISBN, data, `autorId`, nome do autor, `categoriaId` e nome da categoria. O card possui o botão **Ler**, que abre `ler.html?id={id}`. A página consulta somente este endpoint e mostra os metadados completos; o conteúdo textual, capítulos e progresso de leitura ainda não são fornecidos pela API e são apresentados como “em breve”.

## Autores

| Método | Rota | Modelo | Uso na interface |
|---|---|---|---|
| GET | `/api/autores/todos` | `AutorModel.listar` | Autores e formulário de livro |
| GET | `/api/autores/{id}` | `AutorModel.buscarPorId` | Contrato disponível para detalhe |
| GET | `/api/autores?nome=` | `AutorModel.buscarPorNome` | Contrato disponível para pesquisa |
| POST | `/api/autores` | `AutorModel.criar` | Página Adicionar |
| PUT | `/api/autores/{id}` | `AutorModel.atualizar` | Contrato disponível para edição |
| DELETE | `/api/autores/{id}` | `AutorModel.remover` | Contrato disponível para remoção |

Os cards exibem código, nome, nacionalidade, biografia completa e nascimento.

## Categorias

| Método | Rota | Modelo | Uso na interface |
|---|---|---|---|
| GET | `/api/categorias` | `CategoriaModel.listar` | Categorias e formulário de livro |
| GET | `/api/categorias/{id}` | `CategoriaModel.buscarPorId` | Contrato disponível para detalhe |
| POST | `/api/categorias` | `CategoriaModel.criar` | Página Adicionar |
| PUT | `/api/categorias/{id}` | `CategoriaModel.atualizar` | Contrato disponível para edição |
| DELETE | `/api/categorias/{id}` | `CategoriaModel.remover` | Contrato disponível para remoção |

Os cards exibem código, nome, descrição e a quantidade calculada de livros vinculados. O link **Explorar categoria** leva à Biblioteca com o filtro na URL.

## Usuários

| Método | Rota | Modelo | Uso na interface |
|---|---|---|---|
| GET | `/api/usuarios` | `UsuarioModel.listar` | Login e Perfil |
| GET | `/api/usuarios/{id}` | `UsuarioModel.buscarPorId` | Contrato disponível para detalhe |
| GET | `/api/usuarios?nome=` | `UsuarioModel.buscarPorNome` | Contrato disponível para pesquisa |
| POST | `/api/usuarios` | `UsuarioModel.criar` | Criar conta |
| PUT | `/api/usuarios/{id}` | `UsuarioModel.atualizar` | Contrato disponível para edição |
| DELETE | `/api/usuarios/{id}` | `UsuarioModel.remover` | Contrato disponível para remoção |

`UsuarioResponse` contém `id`, nome, sexo, idade, nascimento e matrícula. O Login salva somente a matrícula em `sessionStorage`; não há autenticação, senha ou token nesta fase.
