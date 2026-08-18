<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0F172A,DAA520&height=180&section=header&text=BookVerse&fontSize=54&fontColor=FFFFFF&fontAlignY=36&animation=fadeIn" alt="BookVerse" />

<h1 align="center">📚 BookVerse</h1>

<p align="center">
  Biblioteca digital full stack para descobrir, organizar e acessar livros, autores e categorias.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20desenvolvimento-D4AF37?style=flat-square" alt="Status: em desenvolvimento" />
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=flat-square&logo=springsecurity&logoColor=white" alt="Spring Security com JWT" />
  <img src="https://img.shields.io/badge/JavaScript-ES%20Modules-F7DF1E?style=flat-square&logo=javascript&logoColor=222" alt="JavaScript ES Modules" />
</p>

<p align="center">
  <a href="#sobre-o-projeto">Sobre</a> •
  <a href="#funcionalidades">Funcionalidades</a> •
  <a href="#arquitetura">Arquitetura</a> •
  <a href="#como-executar">Como executar</a> •
  <a href="#segurança">Segurança</a> •
  <a href="#documentação">Documentação</a>
</p>

---

## Sobre o projeto

O **BookVerse** é uma aplicação web de biblioteca digital desenvolvida para demonstrar uma arquitetura full stack organizada. O usuário pode explorar o catálogo, pesquisar livros, conhecer autores e categorias, manter um perfil e acessar fontes externas de leitura. Administradores possuem uma área exclusiva para gerenciar o acervo e consultar o histórico de contas.

O front-end usa **MVC com JavaScript nativo**, separando models, views e controllers. O back-end é uma **API REST em Spring Boot**, com regras de negócio, validações, paginação, migrações de banco e controle de acesso por papel.

## Funcionalidades

### Catálogo e descoberta

- Home com métricas reais do acervo, categorias em destaque e livros recentes;
- Biblioteca com busca por título, autor ou ISBN, filtros de categoria, ordenação e paginação no servidor;
- Cards de livros com capa, categoria, autor, metadados e acesso à página de detalhes;
- Páginas de autores e categorias com dados vinculados ao catálogo;
- Página de leitura identificada por `ler.html?id={id}`.

### Leitura externa

- Cada livro pode receber uma URL externa opcional de leitura;
- Aceita páginas oficiais e links diretos para PDFs públicos usando `http://` ou `https://`;
- A página **Ler** abre a fonte externa em uma nova aba, sem enviar o token do BookVerse;
- Quando não há URL cadastrada, a tela mantém um estado informativo de conteúdo indisponível.

### Administração do acervo

- Cadastro e edição de livros;
- Cadastro de autor e categoria no mesmo fluxo de criação do livro;
- Metadados editoriais: ISBN, descrição, capa, link de leitura, páginas, idioma, editora, edição, classificação etária, status e destaque na Home;
- Estados editoriais: `RASCUNHO`, `EM_REVISAO`, `PUBLICADO` e `ARQUIVADO`;
- Somente obras `PUBLICADO` ficam disponíveis no catálogo comum.

### Contas e perfis

- Criação de conta e login por e-mail e senha;
- Sessão baseada em JWT;
- Perfil editável com nome, informações pessoais e URL de imagem;
- Histórico de usuários para administradores;
- Navbar adaptada ao papel da conta, sem exibir opções administrativas a leitores comuns.

## Permissões

| Recurso | Usuário | Administrador |
|---|:---:|:---:|
| Home, biblioteca, autores e categorias | ✅ | ✅ |
| Ler livros publicados | ✅ | ✅ |
| Editar o próprio perfil | ✅ | ✅ |
| Cadastrar e editar livros | — | ✅ |
| Criar autores e categorias | — | ✅ |
| Abrir rascunhos e obras em revisão | — | ✅ |
| Consultar histórico de usuários | — | ✅ |

> Ocultar opções na interface melhora a experiência, mas a proteção efetiva ocorre no back-end. Chamadas sem autenticação retornam `401`; ações sem o papel necessário retornam `403`.

## Arquitetura

```text
┌────────────────────────────────────────────────────────────┐
│ Front-end                                                    │
│ HTML + CSS + JavaScript MVC                                  │
│ models → controllers → views                                 │
└───────────────────────────┬────────────────────────────────┘
                            │ HTTP + JSON + Bearer JWT
                            ▼
┌────────────────────────────────────────────────────────────┐
│ Back-end                                                     │
│ Spring Boot • Spring Web • Spring Security • JPA             │
│ Controllers → Services → Repositories → H2 (local) /         │
│ PostgreSQL (produção)                                        │
└───────────────────────────┬────────────────────────────────┘
                            │
                            ▼
                   Flyway migrations (V1, V2, V3)
```

### Organização do repositório

```text
bookverse/
├── backend/
│   ├── src/main/java/          # API, entidades, DTOs, serviços e segurança
│   ├── src/main/resources/     # application.yaml e migrações Flyway
│   └── src/test/java/          # testes de integração
├── frontend/
│   ├── assets/css/             # estilos globais e por página
│   ├── assets/js/
│   │   ├── models/             # comunicação com a API
│   │   ├── views/              # renderização da interface
│   │   ├── controllers/        # comportamento das páginas
│   │   └── core/               # sessão, navegação e cliente HTTP
│   └── *.html                  # telas do sistema
├── docs/                       # documentação técnica complementar
└── data/                       # banco H2 local (ignorado pelo Git)
```

## Tecnologias

| Camada | Tecnologias |
|---|---|
| Front-end | HTML5, CSS3, Bootstrap, Bootstrap Icons, JavaScript ES Modules |
| Back-end | Java 21, Spring Boot, Spring Web, Spring Data JPA, Bean Validation |
| Segurança | Spring Security, BCrypt e JSON Web Token (JWT) |
| Dados | H2 local, PostgreSQL em produção, Flyway e suporte a MySQL |
| Qualidade | JUnit, Spring Boot Test e MockMvc |
| Documentação de API | Swagger / OpenAPI |

## API: rotas principais

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/auth/registrar` | Cria uma conta comum e inicia a sessão |
| `POST` | `/api/auth/login` | Autentica e devolve o JWT |
| `GET` | `/api/home` | Dados agregados da página inicial |
| `GET` | `/api/livros` | Catálogo paginado, filtrável e ordenável |
| `GET` | `/api/livros/{id}` | Detalhes de um livro, incluindo `urlLeitura` |
| `POST` | `/api/livros` | Cria um livro — ADMIN |
| `PUT` | `/api/livros/{id}` | Atualiza um livro — ADMIN |
| `GET` | `/api/autores` | Lista autores |
| `GET` | `/api/categorias` | Lista categorias |
| `GET` / `PUT` | `/api/usuarios/me` | Consulta ou edita o próprio perfil |
| `GET` | `/api/admin/usuarios` | Histórico de usuários — ADMIN |

A documentação interativa completa fica disponível pelo Swagger quando a API está em execução.

## Como executar

### Pré-requisitos

- Java 21;
- Maven 3.9 ou superior;
- Um servidor HTTP para o front-end, como a extensão **Live Server** do VS Code.

### 1. Inicie a API

No terminal, a partir da raiz do projeto:

```bash
cd backend
mvn spring-boot:run
```

Na primeira execução, o Flyway cria e atualiza as tabelas automaticamente. A API fica disponível em `http://localhost:8080`.

### 2. Inicie o front-end

Abra a pasta `frontend` no VS Code e execute `index.html` usando o Live Server. Não abra o HTML diretamente pelo explorador de arquivos, pois o projeto usa módulos JavaScript.

O endereço costuma ser `http://127.0.0.1:5500` ou `http://localhost:5500`.

### 3. Acesse o sistema

| Recurso | Endereço |
|---|---|
| Aplicação | URL informada pelo Live Server |
| API | `http://localhost:8080/api` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| Console H2 | `http://localhost:8080/h2-console` |

Para o H2, use a URL JDBC abaixo:

```text
jdbc:h2:file:./data/biblioteca;AUTO_SERVER=TRUE
```

### Executar os testes

```bash
cd backend
mvn test
```

Os testes cobrem a carga das migrações, o catálogo enriquecido, a segurança por papéis e o retorno do link externo de leitura.

## Segurança

O BookVerse utiliza autenticação por e-mail e senha. As senhas são armazenadas com hash **BCrypt**; o navegador recebe apenas um JWT temporário, guardado durante a sessão atual e enviado pelo cabeçalho `Authorization: Bearer {token}`.

Para facilitar demonstrações locais, uma conta administrativa é criada caso ainda não exista:

```text
E-mail: admin@bookverse.local
Senha: admin123
```

> Essas credenciais são apenas para desenvolvimento. Antes de publicar a aplicação, defina valores próprios nas variáveis abaixo e nunca exponha a senha administrativa ou a chave JWT em um repositório público.

```text
BOOKVERSE_ADMIN_EMAIL=seu-email-admin
BOOKVERSE_ADMIN_SENHA=uma-senha-forte
BOOKVERSE_JWT_SECRET=uma-chave-privada-com-pelo-menos-32-caracteres
BOOKVERSE_CORS_ORIGINS=https://seu-dominio.com
```

## Publicação

O repositório está configurado para publicar o front-end no **Vercel** e a API no **Render**, com PostgreSQL persistente. Essa separação é necessária porque o front-end é estático, enquanto a API Java precisa ficar em execução e gravar dados em um banco gerenciado.

1. No Render, importe o repositório como **Blueprint**. O arquivo `render.yaml` cria a API e o banco PostgreSQL, e solicita as variáveis administrativas e a origem permitida no CORS.
2. Após a API estar disponível, copie seu endereço seguido de `/api`, por exemplo `https://bookverse-api.onrender.com/api`.
3. No Vercel, importe o mesmo repositório, selecione `frontend` como **Root Directory** e cadastre `BOOKVERSE_API_URL` com a URL da etapa anterior.
4. Copie a URL final do Vercel para `BOOKVERSE_CORS_ORIGINS` no Render e faça uma nova publicação da API.

As variáveis sensíveis nunca são versionadas. O banco gratuito do Render é adequado para demonstração de portfólio, mas expira após 30 dias; para uma aplicação permanente, escolha um plano com persistência contínua.

## Leitura por PDF ou link externo

O projeto ainda não hospeda arquivos PDF no servidor. Para disponibilizar uma obra, o administrador pode enviar o arquivo para uma fonte autorizada — por exemplo, um armazenamento próprio ou Google Drive com acesso público — e cadastrar a URL no campo **Link externo de leitura**.

Use somente obras de domínio público, materiais próprios ou conteúdos que tenham autorização de distribuição.

Veja o guia completo em [Leitura externa](docs/LEITURA_EXTERNA.md).

## Documentação

- [Arquitetura MVC do front-end](docs/ARQUITETURA_MVC_FRONTEND.md)
- [Rotas e telas](docs/ROTAS_E_TELAS.md)
- [Enriquecimento do catálogo no back-end](docs/ENRIQUECIMENTO_CATALOGO_BACKEND.md)
- [Alterações do back-end](docs/ALTERACOES_BACKEND.md)
- [Segurança e acessos](docs/SEGURANCA_E_ACESSOS.md)
- [Leitura externa](docs/LEITURA_EXTERNA.md)

## Próximas etapas

- Upload próprio de capas — hoje a capa é cadastrada por URL;
- Upload e armazenamento seguro de PDFs no próprio BookVerse;
- Leitura interna por capítulos ou páginas;
- Favoritos, avaliações e progresso de leitura;
- Recuperação e alteração de senha;
- Conta de demonstração com permissões controladas para o portfólio.

## Autor

Desenvolvido por [Caina Henrique](https://github.com/Cxxdev-code).

<p align="center">
  <a href="https://www.linkedin.com/in/caina-henrique/">
    <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn" />
  </a>
  <a href="https://github.com/Cxxdev-code">
    <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub" />
  </a>
</p>

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0F172A,DAA520&height=120&section=footer" alt="" />
