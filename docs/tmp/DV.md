#Documento de Visão

**Sistema de Monitoramento e Gestão de Bolsistas em Projetos Acadêmicos da UFPR**  
**Versão:** 1.0  
**Data:** 19/08/2025  
**Disciplina:** INFO7060 – Tópicos em Engenharia de Software  
**Equipe:**  Grupo 2:  Jaison P. Rezine, Nathália Alves e Pedro H. Gurski

### **1. Propósito**

Este documento apresenta uma visão geral do sistema proposto para monitoramento e gestão de bolsistas vinculados a projetos acadêmicos da Universidade Federal do Paraná (UFPR). O objetivo é padronizar o acompanhamento das atividades e apoiar a gestão técnico-acadêmica dos projetos.

### **2. Escopo do Produto**

O sistema permitirá:

- Registro e acompanhamento de bolsistas por orientadores e coordenadores.
    
- Lançamento semanal de atividades, justificativas e frequência pelos bolsistas.
    
- Aprovação e feedback pelos orientadores.
    
- Visualização de relatórios por setor institucional (PROGRAD, PROGRAP, etc).
    
- Exportação de dados e integração futura com o SIGA.
    

### **3. Definições, Acrônimos e Abreviações**

- **UFPR**: Universidade Federal do Paraná
    
- **SIGA**: Sistema Integrado de Gestão Acadêmica
    
- **PROGRAD, PROGRAP**: Pró-Reitorias envolvidas na gestão dos programas
    

### **4. Visão Geral do Produto**

|Elemento|Descrição|
|---|---|
|**Sistema**|Plataforma web responsiva, com autenticação institucional e dashboards.|
|**Usuários**|Bolsistas, professores orientadores, coordenadores, técnicos institucionais.|
|**Dados**|Atividades realizadas, frequência, avaliações, relatórios, indicadores.|
|**Fronteiras**|Integração com sistema de autenticação; não substitui o SIGA.|

### **5. Características Principais**

#### 5.1. Cadastro e Gerenciamento

- Vinculação de bolsistas a projetos ativos.
    
- Interface para registro de atividades semanais.
    
- Controle de frequência com justificativa.
    

#### 5.2. Avaliação e Feedback

- Aprovação ou edição de lançamentos pelo orientador.
    
- Inserção de observações e devolutivas.
    

#### 5.3. Relatórios e Indicadores

- Geração automática de relatórios mensais/consolidados.
    
- Exportação em formatos padronizados (PDF, CSV).
    
- Painéis com indicadores de engajamento, frequência, desempenho.
    

### **6. Requisitos Não Funcionais (Resumo Inicial)**

|Requisito|Descrição|
|---|---|
|**Usabilidade**|Interface acessível, responsiva e compatível com dispositivos móveis.|
|**Segurança**|Controle de acesso por perfis; integração com o Portal de Sistemas.|
|**Escalabilidade**|Suporte a múltiplas unidades/setores e grandes volumes de usuários.|
|**Interoperabilidade**|API para exportação de dados; possível futura integração com o SIGA.|
|**Manutenibilidade**|Código modular, com documentação técnica e testes automatizados.|

### **7. Atores e Interfaces Externas**

|Ator|Interface|
|---|---|
|Bolsista|Portal com lançamento de atividades, visualização de carga horária.|
|Orientador|Painel de acompanhamento, feedback e aprovação de atividades.|
|Coordenação/Setor|Visualização global, indicadores, emissão de relatórios.|
|Sistema de Autenticação|Autenticação única via Portal de Sistemas.|

### **8. Restrições**

- A primeira versão não terá integração direta com o SIGA, mas deve prever compatibilidade futura.
    
- O sistema deverá ser acessível via navegadores modernos e adaptado a diretrizes de acessibilidade (eMAG, WCAG 2.1).
    

### **9. Suposições**

- O acesso será restrito à comunidade acadêmica da UFPR (login institucional).
    
- Os dados de projetos e bolsas ativas serão inicialmente inseridos manualmente.
    
- Cada orientador poderá acompanhar diversos bolsistas em projetos distintos.
