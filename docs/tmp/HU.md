
### HUs derivadas do épico de aprovação e feedback

- **Visualizar Submissões Pendentes**
    - Como orientador, quero visualizar todas as submissões pendentes de bolsistas, para priorizar o fluxo de avaliações e decidir quais lançamentos serão analisados primeiro.

- **Avaliar Lançamento Semanal**
    - Como orientador, quero avaliar cada lançamento semanal submetido, para garantir aderência à qualidade e às regras do projeto.
    
- **Aprovar Lançamento**
    - Como orientador, quero aprovar lançamentos quando estiverem corretos, para atualizar a situação do bolsista e manter o histórico de conformidade.
    
- **Solicitar Edição**
    - Como orientador, quero solicitar edição de lançamentos que apresentem problemas ou informações incorretas, para assegurar correção e a melhoria contínua do registro.
    
- **Inserir Feedback**
    - Como orientador, quero inserir feedback textual nos lançamentos, para orientar o desenvolvimento dos bolsistas e fornecer devolutivas construtivas.
    
- **Enviar Notificação**
    - Como sistema, quero enviar notificações automáticas ao bolsista sobre o status de seus lançamentos, para assegurar comunicação eficiente e acompanhamento em tempo real.


## Criterios de Aceitacao

### HU: Visualizar Submissões Pendentes

- O orientador deve visualizar todas as submissões pendentes de modo filtrado e atualizado.
- Submissões já avaliadas não aparecem mais na listagem de pendências.
- A lista exibe informações essenciais (nome do bolsista, data, status).
- Se não houver pendências, o sistema apresenta mensagem apropriada.

***

### HU: Avaliar Lançamento Semanal

- Orientador pode examinar detalhes do lançamento (atividade, justificativa, frequência) antes de tomar ação.
- É possível aprovar, solicitar edição ou rejeitar cada lançamento.
- O sistema armazena a decisão e atualiza o status da submissão.

***

### HU: Aprovar Lançamento

- Ao aprovar, o status do lançamento é alterado para “aprovado” e o registro torna-se não editável pelo bolsista.
- O bolsista recebe notificação imediata de aprovação.
- O histórico de aprovações pode ser consultado futuramente.

***

### HU: Solicitar Edição

- Solicitar edição só é permitido com justificativa obrigatória digitada pelo orientador.
- O status muda para “em edição” e o bolsista recebe a justificativa via notificação.
- O bolsista deve conseguir reenviar a submissão alterada para nova análise.

***

### HU: Inserir Feedback

- O feedback textual é registrado e visível tanto para orientador quanto para bolsista na interface.
- Feedbacks ficam associados ao histórico do lançamento.
- Notificações são enviadas sempre que feedback é registrado.

***

### HU: Enviar Notificação

- Notificações são disparadas automaticamente quando o orientador aprovar, solicitar edição ou rejeitar um lançamento.
- A notificação informa status e, quando aplicável, inclui motivo ou feedback.
- O bolsista pode visualizar todas as notificações em área específica do sistema.

