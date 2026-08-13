# Segurança e acessos — BookVerse

## O que foi implementado

O BookVerse agora usa Spring Security com autenticação por e-mail e senha. A senha nunca é devolvida pela API: ela é convertida para um hash BCrypt antes de ser salva no banco.

Após o login, a API devolve um token JWT temporário. O navegador guarda esse token apenas durante a sessão atual e o envia em cada requisição protegida pelo cabeçalho `Authorization: Bearer <token>`.

## Papéis

| Papel | Pode acessar |
|---|---|
| `USUARIO` | Home, Biblioteca, Autores, Categorias, leitura de livros publicados e o próprio Perfil. |
| `ADMIN` | Tudo que o usuário acessa, cadastro e edição de livros, autores/categorias e histórico de usuários. |

O menu esconde as opções administrativas para usuários comuns, mas a proteção verdadeira também está no backend. Portanto, copiar a URL de `adicionar.html` não libera o acesso e chamar uma rota administrativa sem permissão resulta em `403`.

## Rotas de autenticação

| Método | Rota | Uso |
|---|---|---|
| `POST` | `/api/auth/registrar` | Cria uma conta comum e já devolve a sessão. |
| `POST` | `/api/auth/login` | Entra com e-mail e senha. |
| `GET` | `/api/usuarios/me` | Retorna o perfil de quem está autenticado. |
| `PUT` | `/api/usuarios/me` | Atualiza nome, sexo, nascimento e URL da imagem do próprio perfil. |
| `GET` | `/api/admin/usuarios` | Histórico de contas, exclusivo para ADMIN. |

## Administrador local inicial

Na primeira inicialização de uma base nova, o sistema cria uma conta administrativa automaticamente:

```text
E-mail: admin@bookverse.local
Senha: admin123
```

Use esses dados somente no ambiente local de estudo. Antes de publicar o projeto, defina variáveis de ambiente com valores privados:

```text
BOOKVERSE_ADMIN_EMAIL=seu-email-admin
BOOKVERSE_ADMIN_SENHA=uma-senha-forte
BOOKVERSE_JWT_SECRET=uma-chave-com-ao-menos-32-caracteres
BOOKVERSE_CORS_ORIGINS=https://seu-front.com
```

O administrador é criado apenas se ainda não existir uma conta com o e-mail configurado.

## Banco de dados

A migração Flyway `V2__seguranca_usuarios.sql` adiciona ao usuário:

- e-mail único;
- hash de senha;
- papel (`USUARIO` ou `ADMIN`);
- status ativo;
- URL de imagem de perfil;
- data de criação e último acesso.

Usuários antigos continuam registrados, mas não têm senha. Para usar a nova autenticação, crie uma conta pela tela de login com um novo e-mail.

## Imagem de perfil

Atualmente a imagem é uma URL opcional, por exemplo:

```text
https://site-exemplo.com/minha-imagem.jpg
```

Também é aceito um caminho interno do front-end, como `assets/img/perfis/leitor.jpg`. Em uma etapa futura, é possível trocar esse campo por upload de arquivo no backend.

## Livros não publicados

O catálogo público continua exibindo somente livros com status `PUBLICADO`. Um administrador consegue abrir detalhes de rascunhos e livros em revisão; usuários comuns recebem acesso negado para essas obras.

## Como testar

1. Inicie o backend: `cd backend` e `mvn spring-boot:run`.
2. Sirva a pasta `frontend` por um servidor HTTP, como Live Server.
3. Entre como administrador para cadastrar/editar livros e abrir **Usuários**.
4. Crie uma conta comum para validar que o catálogo funciona, mas as telas administrativas ficam bloqueadas.
