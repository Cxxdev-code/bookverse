# Alterações no backend

## Motivo

O front-end MVC precisa receber identificadores para realizar operações por ID e precisa de respostas previsíveis para mostrar erros corretos ao usuário. As mudanças abaixo preservam as rotas existentes e ampliam o contrato quando necessário.

## Contrato de respostas ampliado

### `UsuarioResponse`

Foi adicionado o campo `id`.

| Antes | Depois |
|---|---|
| `nome`, `sexo`, `idade`, `dataNascimento`, `matricula` | `id`, `nome`, `sexo`, `idade`, `dataNascimento`, `matricula` |

Impacto: permite usar com segurança as rotas `GET`, `PUT` e `DELETE /api/usuarios/{id}` a partir da lista de usuários.

### `LivroResponse`

Foram adicionados `autorId` e `categoriaId`, sem remover os nomes amigáveis.

| Antes | Depois |
|---|---|
| `id`, `titulo`, `descricao`, `autor`, `isbn`, `categoria`, `publicado` | `id`, `titulo`, `descricao`, `autorId`, `autor`, `isbn`, `categoriaId`, `categoria`, `publicado` |

Impacto: o front consegue montar o `LivroDto` completo exigido por `PUT /api/livros/{id}`.

## Rotas adicionadas

| Método | Rota | Corpo | Resultado |
|---|---|---|---|
| PUT | `/api/autores/{id}` | `AutorDto` completo | Atualiza e retorna o autor |
| DELETE | `/api/autores/{id}` | — | Exclui e retorna o autor removido |

Um autor com livros vinculados não é excluído: a API retorna `409 Conflict`.

## Correções de regra de negócio

- `PUT /api/livros/{id}` agora atualiza o ISBN.
- Criação e edição de livros validam unicidade de título e ISBN, sem tratar o próprio livro como duplicado.
- Edição de categoria aceita manter o mesmo nome; apenas nomes usados por outra categoria retornam conflito.
- Categorias vinculadas a livros não podem ser removidas e retornam `409 Conflict`.
- `LivroDto.publicado` agora é obrigatório, pois a entidade já exigia data de publicação.
- A descrição do livro suporta até 5.000 caracteres, tanto no modelo de persistência quanto na validação do DTO.

## Erros padronizados

Todas as respostas de erro usam este formato:

```json
{
  "timestamp": "2026-08-09T22:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Existem campos inválidos.",
  "fields": {
    "titulo": "O título é obrigatório"
  }
}
```

`fields` aparece somente em erros de validação.

| Situação | Status |
|---|---:|
| DTO inválido, data/formato malformado ou tipo incompatível | 400 |
| Livro, autor, categoria ou usuário inexistente | 404 |
| Título, ISBN, autor ou categoria duplicados; exclusão com vínculo | 409 |
| Falha não prevista | 500 |

## Política de exclusão

Para evitar perda acidental de dados e erros de chave estrangeira, autores e categorias que possuem livros vinculados não podem ser excluídos. Primeiro é necessário editar ou remover os livros associados. A API responde com `409` e uma mensagem explicativa.

## Validação executada

- `mvn package -DskipTests`: concluído com sucesso.
- Teste HTTP isolado, com banco H2 em memória: criação de autor, categoria, livro e usuário; confirmação dos campos `autorId`, `categoriaId` e `id` nas respostas.
- O livro de teste usou uma descrição de 331 caracteres, confirmando o novo limite acima dos 255 caracteres anteriores.
- Tentativa de excluir o autor já vinculado ao livro: retorno `409 Conflict`, conforme a política de exclusão.

## Compatibilidade

Nenhuma rota existente foi removida. Os campos novos nas respostas são aditivos e não quebram as telas anteriores.
