import { mkdir, writeFile } from "node:fs/promises";
import { dirname, resolve } from "node:path";

const destino = resolve("assets/js/core/api-config.js");
const apiUrl = process.env.BOOKVERSE_API_URL?.trim();

if (process.env.VERCEL && !apiUrl) {
    throw new Error("Defina BOOKVERSE_API_URL no projeto Vercel antes de publicar o front-end.");
}

const urlFinal = (apiUrl || "http://localhost:8080/api").replace(/\/$/, "");
const conteudo = `// Arquivo gerado no build. Nao edite manualmente.\nexport const API_URL_CONFIGURADA = ${JSON.stringify(urlFinal)};\n`;

await mkdir(dirname(destino), { recursive: true });
await writeFile(destino, conteudo, "utf8");
