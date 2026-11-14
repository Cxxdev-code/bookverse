-- O CDN do Goodreads passou a bloquear (403) imagens carregadas por outros sites.
-- Troca as capas afetadas por imagens do Open Library.
UPDATE livros
SET capa_url = 'https://covers.openlibrary.org/b/isbn/8593156630-L.jpg'
WHERE isbn = '8593156630'
  AND capa_url LIKE '%goodreads.com%';

UPDATE livros
SET capa_url = 'https://covers.openlibrary.org/b/id/15129215-L.jpg'
WHERE isbn = '978-6555523560'
  AND capa_url LIKE '%goodreads.com%';
