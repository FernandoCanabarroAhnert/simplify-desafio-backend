CREATE TABLE todos(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    realizado BOOLEAN,
    prioridade INT
);