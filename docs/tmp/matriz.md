
# 
contexto: você é um Analista de Sistemas especializado em Requisitos, no Processo Unificado e em Extreme Programming;
artefatos do projeto em anexo: (Documento de Visao; diagrama_use_case, epico, HU, RF e RNF) ;
acao: fazer a matriz de rastreabilidade dos HUs vs as RFs.

Segue a matriz de rastreabilidade relacionando as Histórias de Usuário (HUs) com os Requisitos Funcionais (RFs), organizada a partir dos artefatos fornecidos[HU.md][RF-e-RNF.md]:


| História de Usuário | RF001 | RF002 | RF003 | RF004 | RF005 | RF006 | RF007 | RF008 |
| :- | :-- | :-- | :-- | :-- | :-- | :-- | :-- | :-- |
| Visualizar Submissões Pendentes |  |  | X |  |  | X | X |  |
| Avaliar Lançamento Semanal |  | X | X |  |  | X | X |  |
| Aprovar Lançamento |  | X | X |  |  | X | X |  |
| Solicitar Edição |  | X | X |  |  | X | X |  |
| Inserir Feedback |  | X | X |  |  | X | X |  |
| Enviar Notificação |  | X | X |  |  | X | X |  |

**Legenda:**

- **RF001:** Cadastro e gerenciamento de bolsistas
- **RF002:** Registro semanal de atividades pelos bolsistas
- **RF003:** Aprovação e feedback de lançamentos pelos orientadores
- **RF004:** Visualização de relatórios por setores institucionais
- **RF005:** Exportação de dados e relatórios
- **RF006:** Autenticação institucional única
- **RF007:** Controle de acesso baseado em perfis
- **RF008:** Inserção e controle de justificativas de frequência


### Observações

- As HUs estão principalmente relacionadas aos RFs 002 e 003, que tratam do fluxo de lançamentos, avaliação, feedback e retorno do orientador ao bolsista.
- RF006 (autenticação) e RF007 (perfis) são suportes transversais para toda operação orientador/bolsista, então aparecem em todas as HUs.
- As demais RFs (exportação, relatórios, justificativas) não estão diretamente envolvidas nessas HUs, a menos que se criem novas histórias relacionadas.
- Matriz pode ser expandida conforme surgirem novas histórias ou mudanças no escopo dos requisitos.

A matriz garante rastreabilidade e alinhamento direto entre requisitos e funcionalidades implementadas, promovendo clareza e controle sobre a cobertura do sistema
