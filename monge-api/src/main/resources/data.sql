-- #################################
-- ### TABELA USUARIO
-- #################################
-- IDs: 1 (Orientador Carlos), 2 (Bolsista Ana), 3 (Bolsista Bruno)
INSERT INTO usuario (id, nome, email, login, perfil, ativo, data_criacao, data_ultimo_acesso) VALUES
(1, 'Prof. Carlos Silva', 'carlos.silva@ufpr.br', 'carlos.silva', 'ORIENTADOR', true, NOW(), NOW()),
(2, 'Ana Julia', 'ana.julia@ufpr.br', 'ana.julia', 'BOLSISTA', true, NOW(), NOW()),
(3, 'Bruno Martins', 'bruno.martins@ufpr.br', 'bruno.martins', 'BOLSISTA', true, NOW(), NOW());

-- #################################
-- ### TABELA ORIENTADOR
-- #################################
-- Vincula o usuario_id=1 ao perfil de Orientador
INSERT INTO orientador (usuario_id, siape, departamento, titulacao, telefone_contato) VALUES
(1, '1234567', 'DEINF - Departamento de Informática', 'Doutorado', '41999998888');

-- #################################
-- ### TABELA BOLSISTA
-- #################################
-- Vincula os usuarios 2 e 3 ao perfil de Bolsista
INSERT INTO bolsista (usuario_id, matricula, cpf, telefone, endereco, carga_horaria_semanal, data_inicio_vinculo, status_bolsa) VALUES
(2, '2023001', '111.222.333-44', '41988887777', 'Rua Exemplo, 123, Curitiba', 20, '2024-03-01', 'ATIVA'),
(3, '2023002', '222.333.444-55', '41977776666', 'Av. Teste, 456, Curitiba', 20, '2024-03-01', 'ATIVA');

-- #################################
-- ### TABELA PROJETO_ACADEMICO
-- #################################
INSERT INTO projeto_academico (id, codigo, titulo, descricao, data_inicio, status_projeto, unidade_vinculada, programa_fomento, orcamento) VALUES
(1, 'PROJ-2024-01', 'Sistema MonGe - Gestão de Bolsistas', 'Desenvolvimento da nova plataforma de gestão de bolsistas da UFPR.', '2024-01-01', 'ATIVO', 'DEINF', 'PROGRAD', 60000);

-- #################################
-- ### TABELA BOLSISTA_PROJETO (Vínculos)
-- #################################
-- Vincula Bolsistas (2, 3) ao Projeto (1) com o Orientador (1)
INSERT INTO bolsista_projeto (id, bolsista_id, projeto_id, orientador_id, data_vinculacao, status_vinculo) VALUES
(1, 2, 1, 1, '2024-03-01', 'ATIVO'), -- Ana (2) no Projeto (1) com Orientador (1)
(2, 3, 1, 1, '2024-03-01', 'ATIVO'); -- Bruno (3) no Projeto (1) com Orientador (1)

-- #################################
-- ### TABELA LANCAMENTO_SEMANAL
-- #################################
-- Um lançamento da Ana (Bolsista 2) para o Projeto (1)
INSERT INTO lancamento_semanal (id, bolsista_id, projeto_id, semana_referencia, data_lancamento, atividades_realizadas, horas_realizadas, status_lancamento, orientador_avaliador_id, feedback_orientador, justificativa_falta, observacoes) VALUES
(1, 2, 1, '2025-10-20', NOW(), 'Desenvolvimento das entidades JPA e Repositórios Iniciais do projeto MonGe.', 10, 'PENDENTE', 1, 'Feedback Teste', 'Justificativa Falta Teste', 'Observações Teste');

-- #################################
-- ### TABELA NOTIFICACAO
-- #################################
-- Notifica o Orientador (1) sobre o Lançamento (1)
INSERT INTO notificacao (id, destinatario_id, lancamento_id, tipo_notificacao, titulo, mensagem, data_envio, lida, url) VALUES
(1, 1, 1, 'LANCAMENTO_PENDENTE', 'Novo Lançamento para Aprovação', 'A bolsista Ana Julia enviou um novo lançamento de horas (10h) para sua aprovação.', NOW(), false, '/api/v1/lancamentos/1');