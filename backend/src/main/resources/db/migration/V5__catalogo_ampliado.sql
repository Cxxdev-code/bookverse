-- Catálogo ampliado: novas categorias, autores e livros, e polimento dos dados existentes.
-- Todos os INSERTs são idempotentes (WHERE NOT EXISTS), então a migração funciona
-- tanto em bases novas quanto em bases que já têm parte destes registros.
-- Capas: Open Library (permite exibir em outros sites). Links de leitura apenas para
-- obras em domínio público (Project Gutenberg).

-- =====================================================================
-- 1. Categorias
-- =====================================================================

-- "Documento Histórico" passa a agrupar obras de pensamento estratégico e político.
UPDATE categoria SET nome = 'Filosofia e Estratégia'
WHERE LOWER(nome) IN ('documento histórico', 'documento historico')
  AND NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Filosofia e Estratégia');

INSERT INTO categoria (nome, descricao) SELECT 'Tecnologia', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Tecnologia');
INSERT INTO categoria (nome, descricao) SELECT 'Filosofia e Estratégia', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Filosofia e Estratégia');
INSERT INTO categoria (nome, descricao) SELECT 'Mitologia Grega', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Mitologia Grega');
INSERT INTO categoria (nome, descricao) SELECT 'Ficção de Fantasia', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Ficção de Fantasia');
INSERT INTO categoria (nome, descricao) SELECT 'Ficção Científica', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Ficção Científica');
INSERT INTO categoria (nome, descricao) SELECT 'Clássicos da Literatura', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Clássicos da Literatura');
INSERT INTO categoria (nome, descricao) SELECT 'Literatura Brasileira', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Literatura Brasileira');
INSERT INTO categoria (nome, descricao) SELECT 'Terror e Horror', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Terror e Horror');
INSERT INTO categoria (nome, descricao) SELECT 'Desenvolvimento Pessoal', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Desenvolvimento Pessoal');
INSERT INTO categoria (nome, descricao) SELECT 'História e Sociedade', 'x' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'História e Sociedade');

UPDATE categoria SET descricao = 'Programação, arquitetura de software e boas práticas para quem constrói tecnologia.' WHERE nome = 'Tecnologia';
UPDATE categoria SET descricao = 'Tratados clássicos sobre poder, estratégia, liderança e a arte de tomar decisões.' WHERE nome = 'Filosofia e Estratégia';
UPDATE categoria SET descricao = 'Epopeias, deuses e heróis da Grécia Antiga que moldaram a literatura ocidental.' WHERE nome = 'Mitologia Grega';
UPDATE categoria SET descricao = 'Mundos imaginários, magia e jornadas extraordinárias para leitores de todas as idades.' WHERE nome = 'Ficção de Fantasia';
UPDATE categoria SET descricao = 'Futuros possíveis, impérios galácticos e as grandes perguntas da ciência e da tecnologia.' WHERE nome = 'Ficção Científica';
UPDATE categoria SET descricao = 'Obras universais que atravessaram gerações e continuam essenciais para entender o ser humano.' WHERE nome = 'Clássicos da Literatura';
UPDATE categoria SET descricao = 'Grandes nomes e histórias da literatura feita no Brasil, do século XIX aos dias de hoje.' WHERE nome = 'Literatura Brasileira';
UPDATE categoria SET descricao = 'Monstros, sombras e o medo do desconhecido nos clássicos do horror gótico.' WHERE nome = 'Terror e Horror';
UPDATE categoria SET descricao = 'Hábitos, produtividade e ferramentas práticas para evoluir um pouco a cada dia.' WHERE nome = 'Desenvolvimento Pessoal';
UPDATE categoria SET descricao = 'Ensaios sobre a trajetória da humanidade, suas sociedades e os rumos do futuro.' WHERE nome = 'História e Sociedade';

-- =====================================================================
-- 2. Autores
-- Datas de nascimento marcadas com "aproximada" usam 1º de janeiro do ano conhecido.
-- =====================================================================

INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Robert C. Martin', 'x', DATE '1952-12-05', 'Estadunidense' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Robert C. Martin');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Andrew Hunt', 'x', DATE '1964-01-01', 'Estadunidense' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Andrew Hunt');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Sun Tzu', 'x', DATE '0500-01-01', 'Chinês' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Sun Tzu');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Homero', 'x', DATE '0007-12-29', 'Grego' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Homero');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Lewis Carroll', 'x', DATE '1832-01-27', 'Britânico' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Lewis Carroll');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'George Orwell', 'x', DATE '1903-06-25', 'Britânico' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'George Orwell');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Machado de Assis', 'x', DATE '1839-06-21', 'Brasileiro' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Machado de Assis');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'J.R.R. Tolkien', 'x', DATE '1892-01-03', 'Britânico' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'J.R.R. Tolkien');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Frank Herbert', 'x', DATE '1920-10-08', 'Estadunidense' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Frank Herbert');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Isaac Asimov', 'x', DATE '1920-01-02', 'Estadunidense' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Isaac Asimov');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Yuval Noah Harari', 'x', DATE '1976-02-24', 'Israelense' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Yuval Noah Harari');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'James Clear', 'x', DATE '1986-01-01', 'Estadunidense' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'James Clear');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Jane Austen', 'x', DATE '1775-12-16', 'Britânica' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Jane Austen');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Aditya Y. Bhargava', 'x', DATE '1988-01-01', 'Estadunidense' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Aditya Y. Bhargava');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'J.K. Rowling', 'x', DATE '1965-07-31', 'Britânica' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'J.K. Rowling');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Mary Shelley', 'x', DATE '1797-08-30', 'Britânica' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Mary Shelley');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Franz Kafka', 'x', DATE '1883-07-03', 'Tcheco' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Franz Kafka');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Antoine de Saint-Exupéry', 'x', DATE '1900-06-29', 'Francês' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Antoine de Saint-Exupéry');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Paulo Coelho', 'x', DATE '1947-08-24', 'Brasileiro' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Paulo Coelho');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Bram Stoker', 'x', DATE '1847-11-08', 'Irlandês' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Bram Stoker');
INSERT INTO autores (nome, biografia, data_nascimento, nacionalidade) SELECT 'Nicolau Maquiavel', 'x', DATE '1469-05-03', 'Italiano' FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM autores WHERE nome = 'Nicolau Maquiavel');

UPDATE autores SET nacionalidade = 'Chinês' WHERE nome = 'Sun Tzu';
UPDATE autores SET biografia = 'Engenheiro de software conhecido como "Uncle Bob". Um dos autores do Manifesto Ágil, ficou famoso por defender código limpo, design orientado a objetos e responsabilidade profissional no desenvolvimento de software.' WHERE nome = 'Robert C. Martin';
UPDATE autores SET biografia = 'Programador, consultor e escritor. Coautor de The Pragmatic Programmer e um dos signatários do Manifesto Ágil, dedica-se a ensinar práticas que tornam desenvolvedores mais eficientes e conscientes do próprio ofício.' WHERE nome = 'Andrew Hunt';
UPDATE autores SET biografia = 'General, estrategista e filósofo da China antiga, tradicionalmente apontado como autor de A Arte da Guerra. Suas ideias sobre vencer pela inteligência, e não pela força, influenciam militares, empresários e líderes há mais de dois milênios.' WHERE nome = 'Sun Tzu';
UPDATE autores SET biografia = 'Poeta épico da Grécia Antiga, a quem a tradição atribui a Ilíada e a Odisseia. Pouco se sabe com certeza sobre sua vida, mas sua obra é considerada a pedra fundamental da literatura ocidental.' WHERE nome = 'Homero';
UPDATE autores SET biografia = 'Pseudônimo de Charles Lutwidge Dodgson, matemático, fotógrafo e escritor inglês. Criou Alice no País das Maravilhas, obra que mistura lógica, jogos de linguagem e nonsense de forma única.' WHERE nome = 'Lewis Carroll';
UPDATE autores SET biografia = 'Pseudônimo de Eric Arthur Blair, jornalista e romancista inglês. Crítico feroz do autoritarismo, escreveu A Revolução dos Bichos e 1984, livros que tornaram expressões como "Grande Irmão" parte do vocabulário mundial.' WHERE nome = 'George Orwell';
UPDATE autores SET biografia = 'Considerado o maior escritor brasileiro, foi fundador e primeiro presidente da Academia Brasileira de Letras. Com ironia fina e olhar psicológico apurado, escreveu clássicos como Dom Casmurro e Memórias Póstumas de Brás Cubas.' WHERE nome = 'Machado de Assis';
UPDATE autores SET biografia = 'Filólogo e professor da Universidade de Oxford, criou a Terra-média, com idiomas, mapas e mitologia próprios. O Hobbit e O Senhor dos Anéis definiram a fantasia moderna.' WHERE nome = 'J.R.R. Tolkien';
UPDATE autores SET biografia = 'Jornalista e escritor norte-americano. Duna, seu romance mais famoso, combina ecologia, política e religião e é frequentemente apontado como a obra de ficção científica mais vendida de todos os tempos.' WHERE nome = 'Frank Herbert';
UPDATE autores SET biografia = 'Bioquímico e um dos escritores mais prolíficos da história, com centenas de livros publicados. Mestre da ficção científica, criou a série Fundação e as famosas Três Leis da Robótica.' WHERE nome = 'Isaac Asimov';
UPDATE autores SET biografia = 'Historiador e professor da Universidade Hebraica de Jerusalém. Seus livros, como Sapiens e Homo Deus, explicam a trajetória da humanidade de forma acessível e provocadora.' WHERE nome = 'Yuval Noah Harari';
UPDATE autores SET biografia = 'Escritor e palestrante especializado em hábitos, tomada de decisão e melhoria contínua. Hábitos Atômicos se tornou um dos livros de desenvolvimento pessoal mais vendidos do mundo.' WHERE nome = 'James Clear';
UPDATE autores SET biografia = 'Romancista inglesa que retratou com humor e ironia a sociedade rural da Inglaterra georgiana. Orgulho e Preconceito é uma das histórias de amor mais lidas e adaptadas da literatura.' WHERE nome = 'Jane Austen';
UPDATE autores SET biografia = 'Engenheiro de software e ilustrador. Ficou conhecido por explicar algoritmos com desenhos e exemplos do dia a dia em Entendendo Algoritmos.' WHERE nome = 'Aditya Y. Bhargava';
UPDATE autores SET biografia = 'Escritora britânica criadora de Harry Potter. A saga sobre o jovem bruxo foi traduzida para dezenas de idiomas e formou uma geração inteira de leitores.' WHERE nome = 'J.K. Rowling';
UPDATE autores SET biografia = 'Escritora inglesa que publicou Frankenstein aos 20 anos. A obra é considerada um marco do romance gótico e um dos primeiros livros de ficção científica.' WHERE nome = 'Mary Shelley';
UPDATE autores SET biografia = 'Escritor de língua alemã nascido em Praga. Suas histórias sobre indivíduos esmagados por sistemas absurdos deram origem ao termo "kafkiano".' WHERE nome = 'Franz Kafka';
UPDATE autores SET biografia = 'Escritor e aviador francês, pioneiro do correio aéreo. Sua experiência nos céus inspirou O Pequeno Príncipe, um dos livros mais traduzidos do mundo.' WHERE nome = 'Antoine de Saint-Exupéry';
UPDATE autores SET biografia = 'Escritor brasileiro e membro da Academia Brasileira de Letras. O Alquimista o tornou um dos autores de língua portuguesa mais lidos e traduzidos de todos os tempos.' WHERE nome = 'Paulo Coelho';
UPDATE autores SET biografia = 'Escritor e administrador teatral irlandês. Com Drácula, consagrou a figura do vampiro na literatura e na cultura popular.' WHERE nome = 'Bram Stoker';
UPDATE autores SET biografia = 'Diplomata, historiador e filósofo político florentino do Renascimento. O Príncipe, seu tratado sobre a conquista e a manutenção do poder, fundou a ciência política moderna.' WHERE nome = 'Nicolau Maquiavel';

-- =====================================================================
-- 3. Livros
-- =====================================================================

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9780132350884', 'Clean Code', 'x', DATE '2008-08-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Tecnologia'), (SELECT MIN(id) FROM autores WHERE nome = 'Robert C. Martin'),
       'https://m.media-amazon.com/images/I/71nj3JM-igL._SL1500_.jpg', NULL, 464, 'Inglês', 'Prentice Hall', '1ª edição', 'Livre', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9780132350884');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9780135957059', 'The Pragmatic Programmer', 'x', DATE '2019-09-13',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Tecnologia'), (SELECT MIN(id) FROM autores WHERE nome = 'Andrew Hunt'),
       'https://m.media-amazon.com/images/I/61ztlXgCmpL._SL1500_.jpg', NULL, 352, 'Inglês', 'Addison-Wesley', '2ª edição', 'Livre', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9780135957059');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '8593156630', 'Arte da Guerra', 'x', DATE '2018-08-06',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Filosofia e Estratégia'), (SELECT MIN(id) FROM autores WHERE nome = 'Sun Tzu'),
       'https://covers.openlibrary.org/b/isbn/8593156630-L.jpg', NULL, 146, 'Português', 'Buzz Editora', '1ª edição', 'Livre', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '8593156630');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '978-6555523560', 'A Odisseia', 'x', DATE '2021-06-14',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Mitologia Grega'), (SELECT MIN(id) FROM autores WHERE nome = 'Homero'),
       'https://covers.openlibrary.org/b/id/15129215-L.jpg', NULL, 398, 'Português', 'Penguin Companhia', '1ª edição', '12 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '978-6555523560');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '978-8594541758', 'Alice no País das Maravilhas', 'x', DATE '2019-08-05',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Ficção de Fantasia'), (SELECT MIN(id) FROM autores WHERE nome = 'Lewis Carroll'),
       'https://m.media-amazon.com/images/I/81eAcV387dL._AC_UF1000,1000_QL80_.jpg', 'https://www.gutenberg.org/ebooks/11', 208, 'Português', 'Darkside Books', '1ª edição', 'Livre', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '978-8594541758');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788535914849', '1984', 'x', DATE '2009-07-21',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Clássicos da Literatura'), (SELECT MIN(id) FROM autores WHERE nome = 'George Orwell'),
       'https://covers.openlibrary.org/b/isbn/9788535914849-L.jpg', NULL, 416, 'Português', 'Companhia das Letras', '1ª edição', '14 anos', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788535914849');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788582850350', 'Dom Casmurro', 'x', DATE '2016-07-16',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Literatura Brasileira'), (SELECT MIN(id) FROM autores WHERE nome = 'Machado de Assis'),
       'https://covers.openlibrary.org/b/isbn/9788582850350-L.jpg', 'https://www.gutenberg.org/ebooks/55752', 400, 'Português', 'Penguin-Companhia', '1ª edição', '12 anos', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788582850350');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788506004234', 'Memórias Póstumas de Brás Cubas', 'x', DATE '2012-01-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Literatura Brasileira'), (SELECT MIN(id) FROM autores WHERE nome = 'Machado de Assis'),
       'https://covers.openlibrary.org/b/isbn/9788506004234-L.jpg', 'https://www.gutenberg.org/ebooks/54829', 272, 'Português', 'Melhoramentos', '1ª edição', '12 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788506004234');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788532503251', 'O Alquimista', 'x', DATE '2000-01-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Literatura Brasileira'), (SELECT MIN(id) FROM autores WHERE nome = 'Paulo Coelho'),
       'https://covers.openlibrary.org/b/isbn/9788532503251-L.jpg', NULL, 247, 'Português', 'Rocco', '1ª edição', 'Livre', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788532503251');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788595084742', 'O Hobbit', 'x', DATE '2019-07-15',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Ficção de Fantasia'), (SELECT MIN(id) FROM autores WHERE nome = 'J.R.R. Tolkien'),
       'https://covers.openlibrary.org/b/isbn/9788595084742-L.jpg', NULL, 336, 'Português', 'HarperCollins Brasil', '1ª edição', '10 anos', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788595084742');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788595084759', 'O Senhor dos Anéis: A Sociedade do Anel', 'x', DATE '2019-11-25',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Ficção de Fantasia'), (SELECT MIN(id) FROM autores WHERE nome = 'J.R.R. Tolkien'),
       'https://covers.openlibrary.org/b/isbn/9788595084759-L.jpg', NULL, 576, 'Português', 'HarperCollins Brasil', '1ª edição', '12 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788595084759');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788532530783', 'Harry Potter e a Pedra Filosofal', 'x', DATE '2017-03-14',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Ficção de Fantasia'), (SELECT MIN(id) FROM autores WHERE nome = 'J.K. Rowling'),
       'https://covers.openlibrary.org/b/isbn/9788532530783-L.jpg', NULL, 208, 'Português', 'Rocco', '1ª edição', '10 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788532530783');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788522005239', 'O Pequeno Príncipe', 'x', DATE '2000-01-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Ficção de Fantasia'), (SELECT MIN(id) FROM autores WHERE nome = 'Antoine de Saint-Exupéry'),
       'https://covers.openlibrary.org/b/isbn/9788522005239-L.jpg', NULL, 93, 'Português', 'Agir', '48ª edição', 'Livre', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788522005239');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788576573135', 'Duna', 'x', DATE '2017-04-28',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Ficção Científica'), (SELECT MIN(id) FROM autores WHERE nome = 'Frank Herbert'),
       'https://covers.openlibrary.org/b/isbn/9788576573135-L.jpg', NULL, 680, 'Português', 'Aleph', '1ª edição', '14 anos', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788576573135');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788576570660', 'Fundação', 'x', DATE '2009-01-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Ficção Científica'), (SELECT MIN(id) FROM autores WHERE nome = 'Isaac Asimov'),
       'https://covers.openlibrary.org/b/isbn/9788576570660-L.jpg', NULL, 320, 'Português', 'Aleph', '1ª edição', '12 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788576570660');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788563560155', 'Orgulho e Preconceito', 'x', DATE '2011-01-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Clássicos da Literatura'), (SELECT MIN(id) FROM autores WHERE nome = 'Jane Austen'),
       'https://covers.openlibrary.org/b/isbn/9788563560155-L.jpg', 'https://www.gutenberg.org/ebooks/1342', 576, 'Português', 'Penguin-Companhia', '1ª edição', 'Livre', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788563560155');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788571646858', 'A Metamorfose', 'x', DATE '2016-01-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Clássicos da Literatura'), (SELECT MIN(id) FROM autores WHERE nome = 'Franz Kafka'),
       'https://covers.openlibrary.org/b/isbn/9788571646858-L.jpg', 'https://www.gutenberg.org/ebooks/5200', 96, 'Português', 'Companhia das Letras', '1ª edição', '12 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788571646858');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788594540188', 'Frankenstein', 'x', DATE '2017-10-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Terror e Horror'), (SELECT MIN(id) FROM autores WHERE nome = 'Mary Shelley'),
       'https://covers.openlibrary.org/b/isbn/9788594540188-L.jpg', 'https://www.gutenberg.org/ebooks/84', 304, 'Português', 'DarkSide Books', 'Edição de luxo', '14 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788594540188');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788566636239', 'Drácula', 'x', DATE '2018-10-24',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Terror e Horror'), (SELECT MIN(id) FROM autores WHERE nome = 'Bram Stoker'),
       'https://covers.openlibrary.org/b/isbn/9788566636239-L.jpg', 'https://www.gutenberg.org/ebooks/345', 580, 'Português', 'DarkSide Books', 'Edição de luxo', '16 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788566636239');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788528601893', 'O Príncipe', 'x', DATE '2001-01-01',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Filosofia e Estratégia'), (SELECT MIN(id) FROM autores WHERE nome = 'Nicolau Maquiavel'),
       'https://covers.openlibrary.org/b/isbn/9788528601893-L.jpg', 'https://www.gutenberg.org/ebooks/1232', 158, 'Português', 'Bertrand Brasil', '1ª edição', 'Livre', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788528601893');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788550804606', 'Arquitetura Limpa', 'x', DATE '2019-04-10',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Tecnologia'), (SELECT MIN(id) FROM autores WHERE nome = 'Robert C. Martin'),
       'https://covers.openlibrary.org/b/isbn/9788550804606-L.jpg', NULL, 432, 'Português', 'Alta Books', '1ª edição', 'Livre', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788550804606');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788575225639', 'Entendendo Algoritmos', 'x', DATE '2017-03-31',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Tecnologia'), (SELECT MIN(id) FROM autores WHERE nome = 'Aditya Y. Bhargava'),
       'https://covers.openlibrary.org/b/isbn/9788575225639-L.jpg', NULL, 264, 'Português', 'Novatec', '1ª edição', 'Livre', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788575225639');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788550807560', 'Hábitos Atômicos', 'x', DATE '2019-10-27',
       (SELECT MIN(id) FROM categoria WHERE nome = 'Desenvolvimento Pessoal'), (SELECT MIN(id) FROM autores WHERE nome = 'James Clear'),
       'https://covers.openlibrary.org/b/isbn/9788550807560-L.jpg', NULL, 322, 'Português', 'Alta Life', '1ª edição', 'Livre', 'PUBLICADO', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788550807560');

INSERT INTO livros (isbn, titulo, descricao, data_publicacao, categoria_id, autor_id, capa_url, url_leitura, numero_paginas, idioma, editora, edicao, classificacao_etaria, status, destaque, criado_em, atualizado_em)
SELECT '9788535928198', 'Homo Deus', 'x', DATE '2016-11-13',
       (SELECT MIN(id) FROM categoria WHERE nome = 'História e Sociedade'), (SELECT MIN(id) FROM autores WHERE nome = 'Yuval Noah Harari'),
       'https://covers.openlibrary.org/b/isbn/9788535928198-L.jpg', NULL, 448, 'Português', 'Companhia das Letras', '1ª edição', '14 anos', 'PUBLICADO', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (SELECT 1 AS u) d WHERE NOT EXISTS (SELECT 1 FROM livros WHERE isbn = '9788535928198');

-- =====================================================================
-- 4. Descrições (novas e existentes) e polimento dos livros originais
-- =====================================================================

UPDATE livros SET descricao = 'Um guia prático sobre como transformar código que apenas funciona em código fácil de ler, testar e manter. Robert C. Martin mostra, com exemplos reais, princípios de nomes significativos, funções pequenas, tratamento de erros e refatoração contínua.', classificacao_etaria = 'Livre' WHERE isbn = '9780132350884';
UPDATE livros SET descricao = 'Um clássico sobre a mentalidade de um desenvolvedor profissional. Em dicas curtas e diretas, Hunt e Thomas falam de responsabilidade, automação, aprendizado contínuo, design flexível e de como evitar armadilhas comuns em projetos de software.', classificacao_etaria = 'Livre' WHERE isbn = '9780135957059';
UPDATE livros SET descricao = 'Escrito há mais de 2.500 anos, este tratado reúne ensinamentos sobre planejamento, liderança, conhecimento do adversário e uso inteligente dos recursos. Suas lições ultrapassaram o campo militar e são aplicadas hoje em negócios, esportes e na vida pessoal.', classificacao_etaria = 'Livre' WHERE isbn = '8593156630';
UPDATE livros SET descricao = 'Depois de dez anos de guerra em Troia, Odisseu enfrenta mais uma década de provações para voltar para casa: o ciclope Polifemo, o canto das sereias, a feiticeira Circe e a ira de Poseidon. Enquanto isso, Penélope resiste aos pretendentes que querem tomar seu trono.', classificacao_etaria = '12 anos' WHERE isbn = '978-6555523560';
UPDATE livros SET descricao = 'Ao seguir um coelho branco apressado, Alice cai em um mundo onde as regras da lógica não funcionam: um chapeleiro louco, um gato que desaparece deixando apenas o sorriso e uma rainha que manda cortar cabeças. Um clássico que encanta crianças e adultos.', classificacao_etaria = 'Livre', url_leitura = COALESCE(url_leitura, 'https://www.gutenberg.org/ebooks/11') WHERE isbn = '978-8594541758';
UPDATE livros SET descricao = 'Na Oceânia, o Grande Irmão vigia tudo e o Partido reescreve o passado. Winston Smith, funcionário do Ministério da Verdade, começa a questionar o regime e arrisca tudo por um pouco de liberdade. Uma das distopias mais influentes do século XX.' WHERE isbn = '9788535914849';
UPDATE livros SET descricao = 'Bentinho, já velho, revisita a própria vida para entender o amor por Capitu, a moça dos "olhos de ressaca". Narrado por um homem tomado pelo ciúme, o romance deixa ao leitor a pergunta que atravessa gerações: Capitu traiu ou não traiu?' WHERE isbn = '9788582850350';
UPDATE livros SET descricao = 'Um defunto autor decide contar a própria vida depois de morto, com ironia, digressões e total sinceridade. O romance que inaugurou o Realismo no Brasil faz uma crítica afiada à elite do Rio de Janeiro do século XIX.' WHERE isbn = '9788506004234';
UPDATE livros SET descricao = 'Santiago, um jovem pastor andaluz, sonha com um tesouro escondido nas pirâmides do Egito e parte em uma viagem que o leva a conhecer um alquimista. Uma fábula sobre seguir a própria Lenda Pessoal e ouvir o coração.' WHERE isbn = '9788532503251';
UPDATE livros SET descricao = 'Bilbo Bolseiro gosta de uma vida tranquila, até que o mago Gandalf e treze anões o convocam para recuperar um tesouro guardado pelo dragão Smaug. A aventura que apresentou a Terra-média ao mundo.' WHERE isbn = '9788595084742';
UPDATE livros SET descricao = 'Frodo herda um anel que guarda o poder do Senhor do Escuro e precisa levá-lo até a Montanha da Perdição para destruí-lo. Ao lado de hobbits, elfos, anões e homens, começa a jornada que se tornou a maior saga da fantasia.' WHERE isbn = '9788595084759';
UPDATE livros SET descricao = 'No dia em que completa onze anos, Harry descobre que é um bruxo e é convidado a estudar em Hogwarts. Entre amizades, aulas de magia e um mistério escondido no castelo, ele começa a desvendar a verdade sobre o próprio passado.' WHERE isbn = '9788532530783';
UPDATE livros SET descricao = 'Um aviador perdido no deserto do Saara conhece um pequeno príncipe vindo de outro planeta. Através das histórias do menino sobre sua rosa, a raposa e os adultos que encontrou, o livro fala de amizade, amor e do que é realmente essencial.' WHERE isbn = '9788522005239';
UPDATE livros SET descricao = 'No planeta desértico Arrakis, única fonte da especiaria mais valiosa do universo, o jovem Paul Atreides vê sua família ser traída e precisa sobreviver entre os misteriosos Fremen. Uma épica de política, religião e ecologia.' WHERE isbn = '9788576573135';
UPDATE livros SET descricao = 'O matemático Hari Seldon prevê a queda do Império Galáctico e trinta mil anos de barbárie. Para reduzir esse período, cria a Fundação, um grupo encarregado de preservar o conhecimento humano. O início de uma das séries mais importantes da ficção científica.' WHERE isbn = '9788576570660';
UPDATE livros SET descricao = 'Elizabeth Bennet é inteligente, espirituosa e nada disposta a se casar por conveniência. Seu encontro com o orgulhoso senhor Darcy começa com antipatia mútua e se transforma em um dos romances mais amados da literatura.' WHERE isbn = '9788563560155';
UPDATE livros SET descricao = 'Certa manhã, Gregor Samsa acorda transformado em um inseto monstruoso. Preso no quarto, ele vê a família passar do espanto ao desprezo. Uma novela curta e perturbadora sobre alienação, trabalho e solidão.' WHERE isbn = '9788571646858';
UPDATE livros SET descricao = 'O cientista Victor Frankenstein dá vida a uma criatura feita de partes de cadáveres e, horrorizado, a abandona. Rejeitado por todos, o ser busca respostas e vingança. Um clássico gótico sobre ciência, responsabilidade e humanidade.' WHERE isbn = '9788594540188';
UPDATE livros SET descricao = 'O advogado Jonathan Harker viaja à Transilvânia para fechar negócio com o conde Drácula e descobre que é prisioneiro de uma criatura das trevas. Contado por meio de cartas e diários, o romance definiu o mito do vampiro.' WHERE isbn = '9788566636239';
UPDATE livros SET descricao = 'Escrito em 1513, este pequeno tratado ensina como um governante conquista, mantém e perde o poder. Direto e sem ilusões, Maquiavel separou a política da moral e fundou o pensamento político moderno.' WHERE isbn = '9788528601893';
UPDATE livros SET descricao = 'Depois de Código Limpo, Robert C. Martin sobe um nível e trata da estrutura dos sistemas. O livro explica princípios como SOLID, limites entre componentes e independência de frameworks para criar software que resiste ao tempo.' WHERE isbn = '9788550804606';
UPDATE livros SET descricao = 'Um guia ilustrado e divertido para aprender algoritmos sem sofrimento. Busca binária, recursão, ordenação, grafos, programação dinâmica e outros temas são explicados com desenhos e exemplos práticos em Python.' WHERE isbn = '9788575225639';
UPDATE livros SET descricao = 'Pequenas mudanças, resultados impressionantes. James Clear mostra como hábitos minúsculos, repetidos todos os dias, se acumulam e transformam a vida, e apresenta um método prático para criar bons hábitos e abandonar os ruins.' WHERE isbn = '9788550807560';
UPDATE livros SET descricao = 'Depois de vencer a fome, as pestes e as guerras, quais serão os próximos objetivos da humanidade? Yuval Noah Harari analisa como a tecnologia, os dados e a busca pela imortalidade podem redefinir o que significa ser humano.' WHERE isbn = '9788535928198';

-- Livros que já existiam na Filosofia e Estratégia passam a usar a categoria renomeada.
UPDATE livros SET categoria_id = (SELECT MIN(id) FROM categoria WHERE nome = 'Filosofia e Estratégia')
WHERE isbn IN ('8593156630', '9788528601893');

-- Garante que nenhum registro fique com o texto provisório.
UPDATE categoria SET descricao = 'Categoria do catálogo BookVerse.' WHERE descricao = 'x';
UPDATE autores SET biografia = NULL WHERE biografia = 'x';
UPDATE livros SET descricao = titulo WHERE descricao = 'x';
