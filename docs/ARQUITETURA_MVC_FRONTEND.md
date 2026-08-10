# Arquitetura MVC do front-end

## Objetivo

O BookVerse usa JavaScript nativo com módulos ES, sem framework ou etapa de compilação. A separação foi criada para que uma tela não misture requisições HTTP, regras de interação e geração de HTML.

```text
frontend/assets/js/
├── app.js
├── core/
│   ├── api-client.js
│   ├── dom.js
│   ├── navegacao.js
│   └── session.js
├── models/
│   ├── livro-model.js
│   ├── autor-model.js
│   ├── categoria-model.js
│   └── usuario-model.js
├── views/
│   ├── livro-view.js
│   ├── autor-view.js
│   ├── categoria-view.js
│   ├── usuario-view.js
│   ├── form-view.js
│   └── auth-view.js
└── controllers/
    ├── home-controller.js
    ├── livros-controller.js
    ├── autores-controller.js
    ├── categorias-controller.js
    ├── adicionar-controller.js
    ├── login-controller.js
    └── perfil-controller.js
```

## Camadas

### Modelos

Os arquivos em `models/` conhecem somente as rotas da API e os DTOs enviados. Cada modelo expõe `listar`, busca por identificador, buscas nomeadas quando disponíveis e operações de criação, edição e remoção presentes no backend.

### Visões

Os arquivos em `views/` renderizam cards, filtros, selects, detalhes e etapas do formulário. Eles não usam `fetch`. Todo texto interpolado em HTML passa por `escaparHtml`.

### Controladores

Os arquivos em `controllers/` mantêm o estado de tela, recebem eventos, chamam modelos e escolhem qual visão renderizar. Erros da API são mostrados como erro, sem serem convertidos silenciosamente em listas vazias.

### Núcleo

- `api-client.js`: cliente HTTP único, com `ApiError` padronizado.
- `dom.js`: seleção de elementos, formatação, mensagens e escape de HTML.
- `navegacao.js`: comportamento compartilhado da busca na navbar.
- `session.js`: matrícula do usuário ativo durante a sessão sem token.

## Inicialização de uma página

Cada HTML declara sua tela no elemento `body`:

```html
<body data-pagina="livros">
```

E carrega somente o inicializador central:

```html
<script type="module" src="assets/js/app.js"></script>
```

`app.js` lê `data-pagina` e inicia o controlador apropriado. Como são módulos ES, o front-end deve ser servido por um servidor HTTP, por exemplo o Live Server da extensão do VS Code; abrir o HTML diretamente pelo explorador pode bloquear importações de módulos.

## Página de leitura

O botão **Ler** presente nos cards direciona para `ler.html?id={id}`. `leitura-controller.js` valida o parâmetro e usa `LivroModel.buscarPorId` para carregar somente o livro solicitado. A tela mostra os metadados reais e reserva uma área explícita para o conteúdo de leitura futuro, sem simular capítulos, arquivo ou progresso que ainda não existem no backend.

## Fluxo de exemplo: cadastrar livro

1. `adicionar-controller.js` valida o formulário e decide entre autor/categoria existente ou novo.
2. Os modelos de autor e categoria criam as dependências quando necessário.
3. `LivroModel.criar` envia o `LivroDto` para a API.
4. A visão atualiza selects, feedback e indicador de etapas.

## Limitação conhecida do cadastro composto

Quando o usuário escolhe criar um autor ou uma categoria durante o cadastro do livro, o navegador envia as requisições em sequência: autor, categoria e livro. Se a última etapa falhar — por exemplo, por título ou ISBN duplicado — os registros criados antes dela permanecem cadastrados, mas sem vínculo.

Isso é uma limitação natural de três chamadas HTTP independentes. Para tornar o fluxo atômico no futuro, o backend deve expor uma rota transacional específica, por exemplo `POST /api/livros/completo`, que receba o livro e os relacionamentos novos em uma única operação. A interface atual mostra o erro corretamente e não oculta os registros já criados.

## Scripts antigos

Os scripts diretos no diretório `assets/js/` foram preservados como referência histórica, mas não são mais carregados pelas páginas. A execução ativa está em `app.js` e nas pastas MVC.
