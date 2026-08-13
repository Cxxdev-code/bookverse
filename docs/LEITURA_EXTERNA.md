# Leitura externa de livros

## Objetivo

Nesta etapa, o BookVerse não armazena o texto ou o PDF dos livros. Cada livro pode ter, opcionalmente, um endereço externo de leitura. Essa escolha evita o armazenamento de arquivos grandes e deixa clara a responsabilidade pelos direitos de distribuição.

## Campo do livro

O atributo `urlLeitura` aceita somente URLs que comecem com `http://` ou `https://` e tem o limite de 2048 caracteres.

Exemplo no cadastro:

```text
https://site-oficial-exemplo.com/livro
```

Uma URL direta para um PDF público também funciona:

```text
https://site-exemplo.com/arquivo-publico.pdf
```

Use apenas páginas oficiais, obras de domínio público ou arquivos para os quais exista autorização de compartilhamento.

## Comportamento da tela Ler

- O card do catálogo continua abrindo `ler.html?id={id}`.
- A tela busca os detalhes do livro pelo ID.
- Quando `urlLeitura` existe, mostra o botão **Abrir leitura externa**.
- O botão abre o endereço em uma nova aba e não envia a sessão do BookVerse para a página externa.
- Quando não existe URL, a tela explica que o conteúdo ainda não está disponível.

## Banco e API

A migração `V3__link_externo_leitura.sql` adiciona a coluna `url_leitura` à tabela `livros`.

O campo faz parte de `LivroDto`, `LivroResponse` e `LivroDetalheResponse`; portanto, pode ser criado e atualizado pela rota administrativa de livros e é devolvido ao front-end na consulta de detalhes.
