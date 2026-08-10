<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0F172A,DAA520&height=180&section=header&text=BookVerse&fontSize=54&fontColor=FFFFFF&fontAlignY=36&animation=fadeIn" alt="BookVerse"/>

<h1 align="center">📚 BookVerse</h1>

<p align="center">
  Uma biblioteca digital para descobrir, organizar e consultar livros, autores e categorias.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20desenvolvimento-D4AF37?style=flat-square" alt="Status em desenvolvimento" />
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/JavaScript-ES%20Modules-F7DF1E?style=flat-square&logo=javascript&logoColor=222" alt="JavaScript" />
</p>

<p align="center">
  <a href="#-sobre-o-projeto">Sobre</a> •
  <a href="#-funcionalidades">Funcionalidades</a> •
  <a href="#-tecnologias">Tecnologias</a> •
  <a href="#-como-executar">Como executar</a> •
  <a href="#-documentação">Documentação</a>
</p>

---

## 🎯 Sobre o projeto

O **BookVerse** é uma aplicação full stack de catálogo de livros. O projeto permite cadastrar e consultar livros, autores e categorias em uma interface visual inspirada em uma biblioteca digital.

O front-end consome uma API REST própria e foi organizado com MVC em JavaScript nativo, separando regras de tela, requisições e renderização dos componentes.

## ✨ Funcionalidades

- Página inicial com métricas, pesquisa, categorias e livros em destaque;
- Catálogo com busca, filtros por categoria, ordenação e paginação no servidor;
- Cadastro de livros com metadados editoriais: capa, ISBN, editora, idioma, edição, páginas e classificação;
- Cadastro de autor e categoria durante o fluxo de criação do livro;
- Página de autores com biografia, nacionalidade, nascimento e total de livros;
- Página de categorias com quantidade de livros vinculados;
- Página de detalhes/leitura vinculada ao ID de cada livro;
- Perfil e fluxo inicial de criação/login de usuário;
- Documentação interativa da API com Swagger UI;
- Migrações de banco de dados com Flyway.

> A página de leitura exibe as informações da obra. Leitura de PDF, capítulos, links protegidos e progresso de leitura serão implementados em uma próxima etapa.

## 🧩 Arquitetura

```text
Frontend (HTML, CSS e JavaScript MVC)
        │
        ▼
API REST (Spring Boot)
        │
        ├── Controllers
        ├── Services
        ├── Repositories
        ├── DTOs e Mappers
        └── Tratamento global de erros
        │
        ▼
Banco H2 + migrações Flyway
```

## 🛠 Tecnologias

<p align="center">
  <img src="https://skillicons.dev/icons?i=java,spring,maven,js,html,css,bootstrap,git,github" alt="Tecnologias do projeto" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat&logo=spring&logoColor=white" alt="Spring Data JPA" />
  <img src="https://img.shields.io/badge/H2-Database-09476B?style=flat" alt="H2 Database" />
  <img src="https://img.shields.io/badge/Flyway-CC0200?style=flat&logo=flyway&logoColor=white" alt="Flyway" />
  <img src="https://img.shields.io/badge/Swagger%20OpenAPI-85EA2D?style=flat&logo=swagger&logoColor=222" alt="Swagger OpenAPI" />
  <img src="https://img.shields.io/badge/MVC-Front--end-1565C0?style=flat" alt="MVC Front-end" />
</p>

## 🚀 Como executar

### Pré-requisitos

- Java 21;
- Maven instalado;
- Uma extensão/servidor HTTP para o front-end, como **Live Server** no VS Code.

### 1. Inicie a API

No terminal, dentro da pasta do projeto:

```bash
cd backend
mvn spring-boot:run
```

A API será iniciada em `http://localhost:8080`. Na primeira execução, o banco H2 e suas tabelas são criados automaticamente.

### 2. Abra o front-end

Abra a pasta `frontend` no VS Code e execute `index.html` com a extensão **Live Server**. Não abra o arquivo HTML diretamente pelo explorador, pois o projeto utiliza módulos JavaScript.

## 🔗 Endereços úteis

| Recurso | Endereço |
|---|---|
| Site | `http://127.0.0.1:5500/frontend/index.html` ou endereço informado pelo Live Server |
| API | `http://localhost:8080/api` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| Console H2 | `http://localhost:8080/h2-console` |

Para o console H2, utilize a URL JDBC configurada no projeto:

```text
jdbc:h2:file:./data/biblioteca;AUTO_SERVER=TRUE
```

## 📁 Estrutura principal

```text
bookverse/
├── backend/
│   ├── src/main/java/       # API REST, entidades, DTOs e regras de negócio
│   └── src/main/resources/  # configuração e migrações Flyway
├── frontend/
│   ├── assets/css/          # estilos por página e componentes reutilizáveis
│   ├── assets/js/           # MVC: models, views, controllers e núcleo
│   └── *.html               # telas do sistema
└── docs/                    # documentação técnica e de rotas
```

## 📖 Documentação

- [Arquitetura MVC do Front-end](docs/ARQUITETURA_MVC_FRONTEND.md)
- [Rotas e telas](docs/ROTAS_E_TELAS.md)
- [Enriquecimento do catálogo no Back-end](docs/ENRIQUECIMENTO_CATALOGO_BACKEND.md)
- [Alterações do Back-end](docs/ALTERACOES_BACKEND.md)

## 🔜 Próximas etapas

- Autenticação com senha, token JWT e controle de sessão;
- Perfis de acesso: usuário e administrador;
- Permissão de cadastro/publicação de livros apenas para administradores;
- Upload e armazenamento de capas;
- Leitura por capítulos, link externo ou arquivo PDF;
- Favoritos, histórico e progresso de leitura;
- Banco de dados de produção, como MySQL ou PostgreSQL.

## 👨‍💻 Autor

Desenvolvido por [Cainã Henrique](https://github.com/Cxxdev-code).

<p align="center">
  <a href="https://www.linkedin.com/in/caina-henrique/">
    <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn" />
  </a>
  <a href="https://github.com/Cxxdev-code">
    <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub" />
  </a>
</p>

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0F172A,DAA520&height=120&section=footer" alt=""/>
