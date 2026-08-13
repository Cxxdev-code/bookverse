-- URL opcional para uma fonte autorizada de leitura (página oficial ou PDF público).
ALTER TABLE livros ADD COLUMN IF NOT EXISTS url_leitura VARCHAR(2048);
