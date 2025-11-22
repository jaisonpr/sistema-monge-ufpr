# Relatório Técnico Consolidado: Auditoria e Qualidade de Código

**Sistema:** MonGe (Monitoramento e Gestão de Bolsistas - UFPR)  
**Escopo:** Full-Stack (`monge-api` e `monge-web`)  
**Versão:** MVP (Release Candidate)  
**Data da Análise:** 22/11/2025  
**Responsável:** Arquitetura de Soluções / QA


## 1 Sumário Executivo

A auditoria estática e dinâmica realizada sobre os artefatos do sistema MonGe revela um cenário de **Risco Elevado** para a implantação em ambiente produtivo. Ambos os módulos (Backend e Frontend) falham em atingir os critérios mínimos de qualidade estabelecidos no *Quality Gate* institucional.

O sistema apresenta uma degradação severa nos índices de **Confiabilidade (Reliability)** — classificado como **"D"** no Backend e **"C"** no Frontend — indicando a presença de bugs lógicos e instabilidade latente. Adicionalmente, a ausência total de testes automatizados (**Cobertura 0.0%**) e violações de acessibilidade impedem a homologação da Release 1.0.


## 2 Análise Volumétrica e Estrutural

A análise dimensional aponta uma discrepância arquitetural significativa entre as camadas:

| Módulo | Tecnologia | Arquivos | SLOC | Densidade | Característica Arquitetural |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Backend** | Java/Spring | 30 | 805 | \~26 linhas/arq | **Alta Granularidade:** Boa adesão ao SRP (*Single Responsibility Principle*). |
| **Frontend** | JS/HTML | 4 | 1.157 | \~289 linhas/arq | **Monolítico:** Indício de "God Class" no `app.js`, concentrando lógica excessiva. |


## 3 Auditoria Técnica: Backend (`monge-api`)

O backend, embora estruturalmente granular, falha em padrões fundamentais de engenharia de software moderna, resultando em baixa confiabilidade e impossibilidade de testes.

### 3.1 Indicadores de Qualidade (Dashboard)

A Figura 1 abaixo evidencia a reprovação nos critérios de Confiabilidade e Cobertura.

*Figura 1: O Backend apresenta Rating D em Confiabilidade, 1 Security Hotspot e 0.0% de Cobertura.*

**Análise dos Indicadores:**

  * **Reliability D:** Indica a presença de falhas críticas de lógica que podem causar *crashes* ou comportamento inesperado.
  * **Security Review:** A presença de **1 Security Hotspot** exige revisão manual imediata (provável configuração insegura ou exposição de dados).
  * **Coverage 0.0%:** A ausência absoluta de testes torna o sistema frágil a regressões.

### 3.2 Detalhamento das Violações (Issues)

A lista de problemas (Figura 2) comprova que os erros são de severidade **Alta** e **Crítica**.

*Figura 2: Predominância de issues de severidade Alta (Círculos Vermelhos) relacionadas à arquitetura (Injeção de Dependência) e lógica (Condicionais).*

**Principais Ofensores Identificados:**

1.  **Impedimento de Testes (`java:S6813`):** Uso disseminado de `@Autowired` em campos privados (visível na Figura 2). Isso acopla o código ao framework Spring e impede testes unitários isolados.
2.  **Observabilidade Comprometida (`java:S106`):** Uso de `System.err` (visível na Figura 2) em vez de Loggers (SLF4J). Isso viola requisitos de auditoria, pois logs em *stdout* se perdem em produção.
3.  **Lógica Frágil (`java:S3972`):** Condicionais mal estruturadas que geram fluxos inalcançáveis.


## 4 Auditoria Técnica: Frontend (`monge-web`)

O cliente web destaca-se positivamente em performance de carga, mas reprova em manutenibilidade, confiabilidade lógica e higiene de código.

### 4.1 Indicadores de Qualidade (Dashboard)

A Figura 3 demonstra que, diferentemente do backend, o frontend já possui **Bugs confirmados**.

*Figura 3: O Frontend exibe Rating C com 3 Bugs confirmados e uma alta densidade de Code Smells (103 issues).*

**Análise dos Indicadores:**

  * **Reliability C (3 Bugs):** Existem falhas funcionais já detectadas estaticamente que impactam o usuário final.
  * **Maintainability A (com ressalvas):** Apesar do rating A, existem **103 Code Smells**, um número muito alto para apenas 4 arquivos, indicando código "sujo".
  * **Duplicações (4.6%):** Percentual aceitável, mas que deve ser monitorado dado o tamanho reduzido do projeto.

### 4.2 Detalhamento das Violações (Issues)

A Figura 4 revela problemas estruturais no JavaScript e complexidade cognitiva.

*Figura 4: Lista de violações focadas em complexidade cognitiva e uso de práticas obsoletas ou perigosas.*

**Principais Ofensores Identificados:**

1.  **Complexidade Cognitiva (`javascript:S3776`):** Funções com muitas ramificações (`if/else`, loops) aninhadas, dificultando a leitura e manutenção.
2.  **Risco de Segurança (`javascript:S1444`):** Uso de `console.log` ou logs inseguros que podem vazar informações no navegador do cliente.
3.  **Variáveis Mortas:** Declarações de variáveis não utilizadas que poluem o escopo e confundem o desenvolvedor.


## 5 Plano de Ação Unificado

Para reverter a reprovação técnica e garantir a sustentabilidade do projeto, estabelece-se o seguinte roadmap de correção obrigatório:

### Fase 1: Saneamento Crítico (Blocking Issues)

*Meta: Eliminar Ratings C/D e Security Hotspots.*

1.  **[Backend]** Implementar **SLF4J** e remover todo `System.out/err` (Ref: Figura 2).
2.  **[Backend]** Resolver issues de lógica condicional e mitigar o **Security Hotspot**.
3.  **[Frontend]** Corrigir os **3 Bugs** funcionais apontados na Figura 3 e remover chamadas de `console.log`.

### Fase 2: Refatoração Estrutural (High Priority)

*Meta: Habilitar Testabilidade e Manutenibilidade.*

1.  **[Backend]** Refatorar *Controllers* para **Injeção via Construtor**, eliminando `@Autowired` em campos (Ref: Figura 2).
2.  **[Frontend]** Modularizar o `app.js`, separando serviços de API (`services/`) da manipulação de DOM (`ui/`) para reduzir a Complexidade Cognitiva.

### Fase 3: Garantia de Qualidade (Medium Priority)

*Meta: Atingir conformidade institucional de cobertura.*

1.  **[Full-Stack]** Implementar testes unitários (JUnit 5 no Back, Jest/Cypress no Front) para elevar a cobertura de **0.0%** para $\ge$ **80%**.


**Parecer Final:** O sistema MonGe, em sua versão atual, encontra-se **INAPTO** para promoção ao ambiente de produção. Recomenda-se o congelamento de novas *features* até a conclusão das Fases 1 e 2 do plano de ação acima.