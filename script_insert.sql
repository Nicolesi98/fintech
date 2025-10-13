
-- Cadastrar os dados de um novo usuário.
INSERT INTO USUARIO (ID, NOME, EMAIL, SENHA, DATA_CADASTRO) VALUES(1, 'Lucas', 'lucas@teste.com', '1234', SYSDATE);


-- Cadastra uma nova conta
INSERT INTO CONTA
(ID, AGENCIA, NUMERO, TIPO_CONTA, SALDO, DATA_CRIACAO)
VALUES(1, 3456, 987653, 'CORRENTE', 0, SYSDATE);


-- Associando a conta recem criada ao usuario
INSERT INTO USUARIO_CONTA
(FK_CONTA_ID, FK_USUARIO_ID)
VALUES(1, 1);

-- Cadastrando categoria para a transacao
INSERT INTO CATEGORIA
(ID, NOME_CATEGORIA, TIPO_CATEGORIA)
VALUES(1, 'Receitas', 'Entrada');

-- Cadastrar os dados de uma nova receita (entrada de dinheiro) para um usuário.
INSERT INTO TRANSACAO
(ID, DESCRICAO, DATA_CRIACAO, VALOR, TIPO_TRANSACAO, FK_CONTA_ID, FK_CATEGORIA_ID)
VALUES(1, 'Salario', sysdate, 2000, 'ENTRADA', 1, 1);

-- Cadastrando categoria para a transacao
INSERT INTO CATEGORIA
(ID, NOME_CATEGORIA, TIPO_CATEGORIA)
VALUES(2, 'Despesas', 'Saida');

-- Cadastrar os dados de uma nova despesa (gasto) de um usuário.
INSERT INTO TRANSACAO
(ID, DESCRICAO, DATA_CRIACAO, VALOR, TIPO_TRANSACAO, FK_CONTA_ID, FK_CATEGORIA_ID)
VALUES(2, 'Compra Mercado', sysdate, 500, 'SAIDA', 1, 2);

-- Cadastrando categoria para a meta - investimento
INSERT INTO CATEGORIA
(ID, NOME_CATEGORIA, TIPO_CATEGORIA)
VALUES(3, 'Investimento', 'Entrada');


-- Cadastrar os dados de um novo investimento feito por um usuário.
INSERT INTO META
(ID, NOME, DATA_CRIACAO_META, VALOR_ALVO, VALOR_ATUAL, STATUS_META, DATA_ALVO, FK_CATEGORIA_ID)
VALUES(1, 'Viagem Internacional', sysdate, 30000, 0, 'ATIVA', DATE '2025-12-25', 3);

INSERT INTO USUARIO_META
(FK_META_ID, ID, FK_USUARIO_ID)
VALUES(1, 1, 1);