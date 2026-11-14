# BookVerse – recriar os servidores

## Ordem certa
1. API + banco no Render  →  2. Front na Vercel  →  3. Ligar os dois (CORS)

## 1) Render (API + Postgres)
1. Suba este projeto atualizado para o GitHub (`render.yaml` na raiz).
2. Render > **New > Blueprint** > escolha o repositório `bookverse`.
3. Preencha quando ele pedir:
   - `BOOKVERSE_ADMIN_EMAIL` → seu e-mail de admin
   - `BOOKVERSE_ADMIN_SENHA` → uma senha forte
   - `BOOKVERSE_CORS_ORIGINS` → por enquanto `https://localhost` (ajusta no passo 3)
4. Clique em **Apply**. Aguarde o deploy (primeiro build leva alguns minutos).
5. Teste no navegador: `https://SUA-API.onrender.com/actuator/health`
   Deve responder `{"status":"UP"}`.
6. Anote a URL da API (ex.: `https://bookverse-api.onrender.com`).

## 2) Vercel (front)
1. Vercel > **Add New > Project** > repositório `bookverse`.
2. **Root Directory:** `frontend`
3. Em **Environment Variables** crie:
   `BOOKVERSE_API_URL` = `https://SUA-API.onrender.com/api`   (com `/api` no final)
4. Deploy. Anote a URL do site (ex.: `https://bookverse-xyz.vercel.app`).

## 3) Ligar os dois (CORS)
No Render > bookverse-api > Environment, edite:
`BOOKVERSE_CORS_ORIGINS` = `https://bookverse-xyz.vercel.app`
(vários separados por vírgula; aceita `https://*.vercel.app`). Salve – ele reinicia.

## Se der erro
- `ERR_CONNECTION_REFUSED ... localhost` → o site está com versão/cache antigo. Faça novo deploy na
  Vercel e abra com Ctrl+Shift+R (ou aba anônima).
- `CORS` no console → `BOOKVERSE_CORS_ORIGINS` não tem exatamente a URL do site (sem `/` no final).
- Primeira requisição demora ~1 min → plano gratuito do Render "dorme"; é normal.
- API caiu do nada → no plano gratuito o Postgres do Render expira (cerca de 30 dias). Crie outro banco.
