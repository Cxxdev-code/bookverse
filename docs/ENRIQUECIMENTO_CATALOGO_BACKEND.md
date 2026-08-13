# Enriquecimento do catálogo — Backend BookVerse

Este documento registra a implementação das tarefas T01 a T10 de enriquecimento de dados para o Front-end. O objetivo é permitir cards mais completos, uma página de detalhes do livro, busca escalável e uma Home que carregue seus dados em uma única chamada.

## Resultado das tarefas

| Tarefa | Resultado |
|---|---|
| T01 | Contratos `LivroCardResponse`, `LivroDetalheResponse` e `PageResponse<T>` criados. `LivroResponse` foi mantido e enriquecido, portanto nenhuma tela atual perde campos. |
| T02 | Migração Flyway `V1__catalogo_enriquecido.sql` criada para campos novos e para bases novas. |
| T03 | `LivroEntity` ganhou metadados editoriais, estado e datas de auditoria. |
| T04 | `LivroDto` e `LivroMapper` atualizados para gravar e devolver os campos novos. |
| T05 | Catálogo público paginado com busca, filtros e ordenação no banco. |
| T06 | Nova rota `GET /api/livros` e rota de detalhe com contrato próprio. |
| T07 | Rota `GET /api/home` criada. |
| T08 | Autor e categoria agora retornam `quantidadeLivros` calculada no banco. |
| T09 | Validações de parâmetros, respostas de erro e validação de compilação adicionadas. |
| T10 | Front-end integrado: Home, Biblioteca, categorias, autores, cadastro e detalhe do livro passaram a usar os contratos novos. |

## Campos novos do livro

Os campos abaixo são opcionais no cadastro. Eles não alteram a entidade `Autor` nem `Categoria`.

| Campo | Uso na interface |
|---|---|
| `capaUrl` | URL da imagem de capa. Quando vazia, o Front-end continua usando sua imagem padrão. |
| `numeroPaginas` | Informação de ficha técnica e futura leitura em PDF. |
| `idioma` | Ficha técnica do livro. |
| `editora` | Ficha técnica do livro. |
| `edicao` | Ficha técnica do livro. |
| `classificacaoEtaria` | Selo de classificação no card/detalhe. |
| `status` | `RASCUNHO`, `EM_REVISAO`, `PUBLICADO` ou `ARQUIVADO`. O catálogo novo exibe apenas `PUBLICADO`. |
| `destaque` | Quando `true`, o livro pode aparecer em destaque na Home. |
| `criadoEm` e `atualizadoEm` | Datas automáticas, úteis para administração e auditoria. |

Enquanto ainda não existe autenticação de administrador, um livro criado sem enviar `status` recebe `PUBLICADO`. Isso preserva o comportamento atual do formulário: salvar um livro o torna visível. Quando a autenticação for adicionada, a regra deve mudar para o servidor aceitar `status` e `destaque` somente de um administrador.

## Rotas novas

### Catálogo público paginado

`GET /api/livros?page=0&size=12&busca=&categoriaId=&autorId=&ordem=recentes`

Parâmetros:

| Nome | Obrigatório | Valores |
|---|---|---|
| `page` | não | índice da página, começa em `0` |
| `size` | não | quantidade por página, de `1` a `50`; padrão `12` |
| `busca` | não | procura em título, ISBN, descrição, autor e categoria |
| `categoriaId` | não | filtra pelo id da categoria |
| `autorId` | não | filtra pelo id do autor |
| `ordem` | não | `recentes`, `antigos`, `titulo_asc` ou `titulo_desc` |

Exemplo de resposta:

```json
{
  "content": [
    {
      "id": 7,
      "titulo": "Clean Code",
      "autor": "Robert C. Martin",
      "autorId": 2,
      "categoria": "Tecnologia",
      "categoriaId": 1,
      "capaUrl": "https://exemplo.com/clean-code.jpg",
      "descricaoResumo": "Boas práticas para escrever código...",
      "publicado": "2008-08-01",
      "numeroPaginas": 464,
      "status": "PUBLICADO",
      "destaque": true
    }
  ],
  "page": 0,
  "size": 12,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true,
  "hasNext": false,
  "hasPrevious": false
}
```

`PageResponse<T>` não é uma tabela do banco. É apenas uma caixa de resposta: `content` contém os itens e os demais campos dizem ao Front-end se há outra página.

### Detalhe de um livro

`GET /api/livros/{id}` devolve `LivroDetalheResponse`, com descrição completa e todos os metadados. Essa é a rota usada por `ler.html?id=7`.

Fluxo visual:

```text
Card do livro (id 7) -> ler.html?id=7 -> GET /api/livros/7 -> preencher a página
```

Essa tela ainda é uma página de informações, não um leitor de PDF. O conteúdo digital será uma etapa separada, para não misturar os metadados do livro com o arquivo ou link de leitura.

### Home

`GET /api/home` devolve em uma chamada:

- `totais`: livros publicados, autores e categorias;
- `destaques`: até 6 livros publicados marcados como destaque;
- `recentes`: até 6 livros publicados mais recentes;
- `categorias`: até 6 categorias com quantidade de livros.

### Contadores

As rotas de autor e categoria retornam agora `quantidadeLivros`:

- `GET /api/autores/todos`
- `GET /api/autores/{id}`
- `GET /api/categorias`
- `GET /api/categorias/{id}`

Os totais são calculados com consulta de agregação no banco, sem carregar todos os livros de cada autor/categoria.

## Compatibilidade com o Front-end existente

As rotas antigas foram preservadas para a transição gradual:

| Rota atual | Situação |
|---|---|
| `GET /api/livros/todos` | continua retornando uma lista; agora cada item tem os campos novos também |
| `GET /api/livros?titulo=...` | continua como busca legada por título |
| `POST /api/livros` | continua aceitando os campos antigos; os novos são opcionais |
| `PUT /api/livros/{id}` | continua aceitando os campos antigos; os novos são opcionais |

## Integração concluída do Front-end (T10)

- A Home usa `GET /api/home`, reduzindo três chamadas para uma.
- A Biblioteca usa `GET /api/livros` com pesquisa, filtro, ordenação e paginação realizadas no servidor.
- Os cartões usam `descricaoResumo`, `capaUrl` e `numeroPaginas`; mantêm uma imagem padrão se não houver capa.
- A página `ler.html?id=...` mostra os novos dados editoriais retornados pelo detalhe do livro.
- Categorias e autores usam `quantidadeLivros` retornado pelo Backend, sem baixar a lista completa de livros para contar localmente.
- O formulário Adicionar permite preencher metadados opcionais e status editorial.

As rotas antigas permanecem por compatibilidade. Quando todas as instalações do Front-end estiverem atualizadas, `GET /api/livros/todos` e a busca legada por `titulo` podem ser descontinuadas numa versão futura.

## Banco e migração

O projeto agora usa Flyway. Na primeira inicialização:

- em uma base já existente, o Flyway registra uma linha de base e aplica os novos campos;
- em uma base nova, cria a estrutura mínima do catálogo e o Hibernate completa a estrutura restante;
- livros antigos recebem `status = PUBLICADO`, portanto continuam aparecendo no site.

Antes de iniciar em uma base com dados importantes, faça uma cópia do arquivo H2 em `data/`. Em produção, mantenha as migrações SQL versionadas e use uma cópia de segurança do banco antes de qualquer atualização.

## Validação realizada

- Compilação Maven concluída com sucesso.
- Suíte `mvn test` concluída com sucesso.
- Teste de integração do catálogo valida os filtros, o estado `PUBLICADO`, metadados, Home e contadores.
- Teste da migração valida uma base H2 legada: o livro existente permanece e recebe os novos campos com valores seguros.
- Os contratos antigos foram mantidos e os campos novos são aditivos.
