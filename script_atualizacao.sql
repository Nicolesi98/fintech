

-- Alterar os dados de um usuário (como nome, e-mail e etc.). 
-- Ou seja, atualizar os dados de um usuário, a partir do seu código de referência.
UPDATE USUARIO
SET NOME='Gabriel', EMAIL='gabriel@teste.com', SENHA='9876'
WHERE ID=1;


-- Alterar os dados de uma receita já cadastrada, 
-- a partir do código da tabela, ou seja, utilize o código do usuário e o código da receita.
UPDATE TRANSACAO
SET DESCRICAO='13 salario', VALOR=3000, TIPO_TRANSACAO='ENTRADA', FK_CONTA_ID=1, FK_CATEGORIA_ID=1
WHERE ID=1 AND FK_CONTA_ID=1;

-- Alterar os dados de uma despesa já cadastrada, 
-- a partir do código da tabela, ou seja, utilize o código do usuário e o código da despesa.
UPDATE TRANSACAO
SET DESCRICAO='Mercado', VALOR=600, TIPO_TRANSACAO='SAIDA', FK_CONTA_ID=1, FK_CATEGORIA_ID=2
WHERE ID=2 AND FK_CONTA_ID=1;

-- Alterar os dados de um investimento já cadastrado, 
-- a partir do código da tabela, ou seja, utilize o código do usuário e o código do investimento.
UPDATE META
SET NOME='Viagem', VALOR_ALVO=10000, VALOR_ATUAL=30, STATUS_META='ATIVO', DATA_ALVO= DATE '2025-12-31', FK_CATEGORIA_ID=3
WHERE ID=1;

