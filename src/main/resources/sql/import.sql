CREATE TABLE todos(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    realizado BOOLEAN,
    prioridade INT
);

INSERT INTO todos (nome, descricao, realizado, prioridade) VALUES
('Comprar mantimentos', 'Ir ao supermercado e comprar comida para a semana', false, 2),
('Estudar para o exame', 'Revisar todos os capítulos do livro de matemática', false, 1),
('Fazer exercícios físicos', 'Correr por 30 minutos todos os dias', false, 3),
('Pagar contas', 'Efetuar o pagamento das contas de luz e água', false, 2),
('Limpar a casa', 'Aspirar e passar pano no chão de todos os cômodos', false, 2);