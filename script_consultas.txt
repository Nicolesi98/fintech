
--Consultas Simples

-- Consultar os dados de um usuário específico (usando o código dele).
SELECT * FROM USUARIO WHERE ID = 1;

--Consultar os dados de uma despesa específica (usando o código do usuário e o da despesa).
SELECT * FROM TRANSACAO WHERE ID = 2 AND FK_CONTA_ID = 1;

-- Consultar os dados de um investimento específico (com código do usuário e do investimento).
SELECT * FROM META WHERE ID = 1;

--Consultas Ordenadas

--Consultar todas as despesas de um usuário, 
--ordenando da mais nova para a mais antiga. 
--Ou seja, da mais recente para a mais antiga.

SELECT t.* 
FROM transacao t 
JOIN usuario_conta uc ON t.fk_conta_id = uc.fk_conta_id
JOIN usuario u ON u.id = uc.fk_usuario_id
WHERE u.id = 1 AND t.fk_categoria_id = 2 
ORDER BY DATA_CRIACAO DESC

-- Consultar todos os investimentos de um usuário, 
--do mais novo para o mais antigo. 
--Ou seja, do investimento mais recente para o mais antigo.

SELECT m.*
FROM META m
JOIN USUARIO_META um ON m.id = um.FK_META_ID 
JOIN USUARIO u ON u.id = um.FK_USUARIO_ID 
WHERE u.id = 1
ORDER BY DATA_CRIACAO_META DESC

-- Consulta para o Dashboard

-- Consultar as informações principais de um usuário, 
--junto com a última despesa e o último investimento feitos deste usuário. 
--Ou seja, essa consulta deve retornar apenas uma linha, 
--com as principais informações de um usuário, assim como informações da última despesa 
--e do último investimento.

SELECT 
    u.nome AS nome_usuario,
    d.descricao AS descricao_despesa, 
    d.VALOR AS valor_despesa,
    m.nome AS investimento,
    m.VALOR_ATUAL AS valor_atual_investido
FROM TRANSACAO d
JOIN USUARIO_CONTA uc ON uc.FK_CONTA_ID = d.fk_conta_id
JOIN USUARIO u ON u.id = uc.FK_USUARIO_ID 
JOIN USUARIO_META um ON um.FK_USUARIO_ID = u.ID 
JOIN META m ON m.id = um.FK_META_ID
WHERE u.id = 1 
  AND d.FK_CATEGORIA_ID = 2
  AND d.id = (
      -- Última transação para este usuário e categoria
      SELECT MAX(t.id) 
      FROM TRANSACAO t
      JOIN USUARIO_CONTA uc2 ON uc2.FK_CONTA_ID = t.fk_conta_id
      WHERE uc2.FK_USUARIO_ID = u.id 
        AND t.FK_CATEGORIA_ID = 2
  )
  AND m.id = (
      -- Última meta para este usuário
      SELECT MAX(m2.id)
      FROM META m2
      JOIN USUARIO_META um2 ON um2.FK_META_ID = m2.id
      WHERE um2.FK_USUARIO_ID = u.id
  );