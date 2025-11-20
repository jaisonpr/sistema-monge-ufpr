# Relatório de Avaliação de Documentação e Planejamento de Testes

**Sistema MonGe - Monitoramento e Gestão de Bolsistas**

**Data:** 20 de novembro de 2025  
**Papel:** Analista de Qualidade  
**Período de Análise:** Artefatos de Documentação Fornecidos  
**Status:** Documentação Estrutural Completa | Planejamento de Testes Pendente



## Sumário Executivo

O projeto MonGe possui uma base de documentação sólida e bem estruturada, cobrindo requisitos, arquitetura, modelagem de dados e componentes de sistema.  
Porém, o planejamento específico de testes, cenários de validação e critérios de aceite mensuráveis ainda **não está materializado em artefatos formais**.

Este relatório sintetiza os pontos fortes da documentação atual e explicita as lacunas críticas que precisam ser preenchidas para que o projeto avance com qualidade de teste garantida.



## 1. Documentação Existente: Avaliação Positiva

### 1.1 Artefatos de Requisitos e Visão

A documentação contempla os artefatos fundamentais de engenharia de requisitos:

- **Documento de Visão (DV)**: Define propósito, escopo, restrições técnicas, critérios de sucesso e riscos de alto nível do projeto \[1\]
- **Requisitos Funcionais e Não Funcionais (RF/RNF)**: 8 requisitos funcionais (RF001 a RF008) e 5 não funcionais (RNF001 a RNF005) com descrições claras \[1\]
- **Histórias de Usuário (HUs)**: 6 HUs derivadas do épico de aprovação e feedback, com critérios de aceitação explícitos \[2\]
- **Regras de Negócio (RNs)**: 15+ regras de negócio associadas aos fluxos de cadastro, avaliação, aprovação e notificação \[3\]
- **Matriz de Rastreabilidade HU ↔ RF ↔ RN**: Correlação entre histórias, requisitos funcionais e regras, facilitando rastreabilidade \[4\]

**Qualidade observada:** Alta coesão entre artefatos; ausência de ambiguidades maiores; critérios de aceite testáveis nas HUs \[2\]\[4\].



### 1.2 Artefatos de Arquitetura e Modelagem

A estrutura técnica está bem documentada:

- **Documento de Arquitetura (DA)**: Descreve protocolos de comunicação (REST, OAuth2), camadas de aplicação e princípios de interoperabilidade \[5\]
- **Diagrama de Componentes**: Identifica componentes Frontend, Backend, Autenticação Institucional, SIGA e Banco de Dados \[6\]
- **Diagrama de Implantação**: Mostra distribuição física em cloud (Nginx, Spring Boot, PostgreSQL, servidores de autenticação) \[6\]
- **Diagrama de Entidade-Relacionamento (DER)**: Modela entidades (Usuário, Bolsista, Orientador, LançamentoSemanal, Notificação) e relacionamentos \[7\]
- **Diagrama de Casos de Uso**: Representa atores (Orientador, Bolsista, Perfil de Usuário) e fluxos principais de interação \[8\]

**Qualidade observada:** Visões múltiplas cobrindo lógica, dados e física; componentes bem separados; padrões arquiteturais claros \[5\]\[6\]\[7\].



### 1.3 Artefatos de Planejamento e Governança

O projeto define escopo e cronograma:

- **Termo de Abertura de Projeto (TAP)**: Justificativa, objetivos, escopo, restrições, riscos e stakeholders mapeados \[9\]
- **Cronograma de Desenvolvimento**: Fases bem definidas (eng. de req., projeto, arquitetura, POO, testes), com marcos principais até outubro 2025 \[10\]
- **Mapa Fase × Artefato**: Rastreamento de quais documentos nascem em cada fase do ciclo de vida \[11\]

**Qualidade observada:** Planejamento alinhado ao Processo Unificado (UP); marcos claros; integração de atividades de teste prevista \[10\]\[11\].



## 2. Lacunas Críticas: Artefatos Faltantes para Testes

### 2.1 Plano de Testes (CRÍTICO)

**Status:** ❌ NÃO EXISTE

Um plano de testes formal deve detalhar:

- **Estratégia de testes**: Níveis de teste esperados (unitário, integração, sistema, aceitação), responsabilidades e cronograma \[12\]
- **Tipos de teste por camada**: Quais testes aplicam-se a Frontend (UI, usabilidade), Backend (REST, lógica) e Banco de Dados (integridade) \[12\]
- **Critérios de entrada e saída**: Quando testes começam, como se considera um nível completo, métricas de sucesso \[12\]
- **Rastreabilidade teste–requisito**: Mapeamento explícito de cada teste aos RFs, RNs e HUs que valida \[13\]
- **Riscos e priorização**: Quais fluxos e funcionalidades têm maior risco e devem ser testados primeiro \[12\]

**Impacto na qualidade:** Sem plano, testes podem ficar desorganizados, com cobertura irregular e falta de responsabilidades claras entre equipes.



### 2.2 Cenários de Teste Funcionais (CRÍTICO)

**Status:** ❌ NÃO EXISTEM**

Cada HU e RF deve ter cenários de teste formalizados em documento específico, incluindo, por exemplo:

#### Exemplo para HU "Visualizar Submissões Pendentes"

| Cenário                                   | Pré-condições                                      | Passos                                                        | Resultado Esperado                                                           | Dados de Teste     |
| -- | -- | - | - |  |
| C001 - Listar pendências com autorização  | Orientador autenticado; ≥ 2 lançamentos pendentes | 1. Acessar dashboard; 2. Clicar "Submissões Pendentes"       | Lista filtrada com status = PENDENTE, com bolsista, data e frequência       | login=orientador01 |
| C002 - Sem pendências                     | Orientador autenticado; 0 lançamentos pendentes   | 1. Acessar dashboard; 2. Clicar "Submissões Pendentes"       | Mensagem "Nenhuma submissão pendente" exibida                               | login=orientador02 |
| C003 - Acesso negado (não orientador)     | Bolsista autenticado                               | 1. Tentar acessar `/api/submissoes/pendentes`                | HTTP 403 Forbidden com mensagem "Acesso negado"                              | login=bolsista01   |

**Falta:** Catálogo não existe para nenhuma das 6 HUs do épico de aprovação/feedback, nem para os RFs gerais de cadastro, relatórios e exportação \[2\]\[4\].

**Impacto:** Sem cenários explícitos, testes dependem de memória, há variação de cobertura e risco de regressão quando requisitos mudam.



### 2.3 Casos de Teste de Não-Funcionalidade (CRÍTICO)

**Status:** ❌ NÃO EXISTEM

Os RNF estão documentados no artefato RF/RNF, mas sem casos de teste derivados \[1\]:

- **RNF001 – Usabilidade WCAG 2.1 AA**  
  Falta: checklist de validação de contraste, navegação por teclado, `alt` em imagens, `aria-label` em formulários.

- **RNF002 – Autenticação OAuth2 + OIDC**  
  Falta: testes de fluxo de login, validação de token, refresh de sessão, logout limpo.

- **RNF003 – Desempenho (tempo resposta < 2 s)**  
  Falta: testes de carga com ferramentas (JMeter, Locust), limites de usuários simultâneos, latência máxima aceitável.

- **RNF004 – Interoperabilidade com SIGA**  
  Falta: testes de integração REST, validação de payload JSON, tratamento de erros de integração.

- **RNF005 – Manutenibilidade (cobertura ≥ 80%)**  
  Falta: diretrizes de teste unitário, cobertura de ramos críticos, análise estática (SonarQube).

**Impacto:** Sem testes de RNF, qualidades importantes (segurança, desempenho, usabilidade) ficam intocadas até produção.



### 2.4 Matriz de Rastreabilidade Expandida (Teste)

**Status:** ⚠️ PARCIAL

Há matriz HU ↔ RF ↔ RN, mas falta ampliação com eixo de testes, por exemplo:

| HU                               | RF    | RN                  | Casos de Teste                      | Tipo           | Status |
| -- | -- | - | -- | -- |  |
| Visualizar Submissões Pendentes | RF003 | RN006, RN010–RN012  | C001, C002, C003                    | Funcional      | ❌     |
| Aprovar Lançamento              | RF003 | RN011, RN012–RN014  | C010, C011, C012                    | Funcional      | ❌     |
| ...                              | ...   | ...                 | ...                                 | ...            | ❌     |
| (Todos os RNF)                  | —     | RNF001–RNF005       | CT-USR-001, CT-SEC-001, CT-PERF-001 | Não-funcional  | ❌     |

Sem essa expansão, fica difícil responder: "Este teste está mapeado a algum requisito?" e "Qual RF não tem cobertura?".



### 2.5 Diretrizes de Teste Unitário e Cobertura de Código

**Status:** ❌ NÃO EXISTEM

Faltam:

- Mínimo esperado de cobertura de testes por camada (ex.: backend ≥ 80%, frontend ≥ 60%) \[14\]
- Checklist de "o que testar" para classes críticas (serviços de aprovação, validação de lançamento) \[14\]
- Definição de quais ferramentas usar (JUnit + Mockito para Java, Jest para JavaScript) \[14\]
- Critérios de aceite para Pull Request (todos os testes passam, SonarQube com grade ≥ B, cobertura ≥ 80%) \[14\]

**Impacto:** Código entra em produção com qualidade inconsistente; refatorações quebram sem saber.



### 2.6 Checklist de Revisão de Requisitos e Modelos

**Status:** ❌ NÃO EXISTE

Antes de codificar, QA deve revisar documentação. Falta um checklist como:

- [ ] Cada HU tem critério de aceitação testável?  
- [ ] Cada RF é coberto por ≥ 1 HU?  
- [ ] Cada RN tem caso de teste relacionado?  
- [ ] Há ambiguidades ou conflitos no DER vs. classes?  
- [ ] Protocolos de integração (SIGA, autenticação) têm exemplos de payload?  
- [ ] Todos os RNF têm métricas mensuráveis (não são subjetivos)?

Sem isso, problemas em requisitos chegam ao código.



## 3. Análise de Completude por Fase

Segundo o mapa fase × artefato do projeto \[11\]:

| Fase              | Artefatos Esperados                                                                                  | Status      | Lacunas                                                      |
| -- | - | -- |  |
| **Eng. Requisitos** | HUs, Protótipos, Diagrama de Classes, Casos de Uso, Matriz de Rast., **Plano de Teste de Sistema** | ✓✓ / ❌     | **Plano de Testes de Sistema não existe**                   |
| **Modelagem/Arquit** | Componentes, DER, Implantação, Matriz de Rast., **Plano de Teste de Integração**                  | ✓✓ / ❌     | **Plano de Testes de Integração não existe**                |
| **POO**           | Código + Doc, Matriz de Rast., **Diretriz de Teste Unitário**                                       | Em andamento | **Diretrizes de Teste Unitário ainda não definidas**        |



## 4. Recomendações Prioritárias para QA

### P1 (Imediato – antes de iniciar codificação)

1. **Elaborar Plano de Testes** (1–2 dias de trabalho)  
   - Definir estratégia: unitário (80%), integração (60%), sistema (100% HUs do épico).  
   - Alocação de testes por fase e responsável.  
   - Critérios de entrada/saída e métricas \[12\]\[13\].

2. **Derivar Cenários Funcionais do Épico de Aprovação/Feedback** (2–3 dias)  
   - Mínimo: 3–4 cenários por HU (caminho feliz, borda, erro).  
   - 6 HUs × 3 cenários ≈ 18 casos de teste funcionais \[2\]\[4\].  
   - Formato: tabela com pré-condição, passos, resultado esperado, dados.

3. **Definir Testes de RNF** (1–2 dias)  
   - Para cada RNF (usabilidade, segurança, desempenho, etc.), listar testes aplicáveis.  
   - Ferramentas: eMAG (acessibilidade), OWASP ZAP (segurança), JMeter (carga).  
   - Métricas mensuráveis (ex.: contraste ≥ 4.5:1, resposta < 2 s, 100 usuários/s).



### P2 (Durante arquitetura e POO)

4. **Expandir Matriz de Rastreabilidade com eixo de Testes** (1 dia)  
   - Manter viva a ligação HU → RF → RN → Casos de Teste.  
   - Facilitar auditoria de cobertura.

5. **Definir Diretrizes de Teste Unitário e Cobertura** (1–2 dias)  
   - Mínimo 80% backend, 60% frontend.  
   - Regras de análise estática (SonarQube, ESLint).  
   - Validação em Pull Request.



### P3 (Antes de cada release)

6. **Executar Testes e Registrar Defeitos** (contínuo)  
   - Usar plano e cenários para executar sistematicamente.  
   - Rastrear bugs à HU/RF/RN que violou.

7. **Revisar Requisitos com Checklist QA** (antes de POO)  
   - Garantir que a entrada para codificação está limpa.



## 5. Impacto da Falta desses Artefatos

| Risco                                                         | Probabilidade | Impacto | Mitigação Proposta                           |
| - | - | - | -- |
| Requisitos mal interpretados em código                        | Alta          | Alto    | Plano + cenários + revisão                   |
| Testes com cobertura irregular (alguns RFs intocados)         | Alta          | Alto    | Matriz de teste + rastreabilidade            |
| Defeitos em produção (ex.: falha em aprovação de lançamento)  | Média         | Crítico | Testes funcionais + integração               |
| Desempenho inadequado (ex.: lentidão em relatórios)           | Média         | Alto    | Testes não-funcionais de carga               |
| Refatorações quebram código sem saber                         | Média         | Médio   | Teste unitário com cobertura ≥ 80%           |
| Usuários impossibilitados de navegar (acessibilidade violada) | Baixa         | Médio   | Testes de acessibilidade WCAG 2.1 AA         |



## 6. Conclusão

A documentação de requisitos, arquitetura e modelagem do MonGe está **bem estruturada e completa para um sistema acadêmico**.  
Não há falta de artefatos conceituais.

**Porém, a ponte entre especificação e validação (testes) está aberta.**  
Sem plano de testes, cenários, métricas de RNF e diretrizes de cobertura, a equipe de desenvolvimento não terá referência clara do que validar, e o risco de defeitos sobe.

**Próximo passo urgente:** converter essa documentação de requisitos em **documentação de testes** – formalizando estratégia, casos, métricas e rastreabilidade – garantindo que cada linha de código implementada possa ser verificada contra um critério de teste específico.

Com isso, o projeto MonGe estará pronto para entrar na fase de POO com confiança de que a qualidade será garantida desde a primeira linha codificada.



## Referências

\[1\] **RF-e-RNF.md** — Requisitos Funcionais e Não-Funcionais do MonGe  
\[2\] **HU.md** — Histórias de Usuário derivadas do épico de aprovação e feedback  
\[3\] **RNs.md** — Regras de Negócio associadas às HUs e RFs  
\[4\] **matriz.md** — Matriz de Rastreabilidade HU ↔ RF ↔ RN  
\[5\] **DA.md** — Documento de Arquitetura (protocolos, componentes, princípios)  
\[6\] `components.drawio.jpg`, `deployment_gemini.jpg` — Diagramas de Componentes e Implantação  
\[7\] `MonGe_DER.jpg` — Diagrama de Entidade-Relacionamento  
\[8\] `diagrama_use_case.jpg` — Diagrama de Casos de Uso  
\[9\] **TAP.md** — Termo de Abertura de Projeto  
\[10\] **cronograma.md** — Cronograma de Desenvolvimento (fases e marcos)  
\[11\] **fase_artefato.md** — Mapa de Artefatos por Fase do UP  
\[12\] *IEEE Std 829-2008 – Standard for Software and System Test Documentation*  
\[13\] *ISTQB Certified Tester Foundation Level* — Estratégia e níveis de teste  
\[14\] *Clean Code & TDD Best Practices* — Cobertura de testes e diretrizes unitárias  

**Documento preparado por:** Analista de Qualidade  
**Data:** 20 de novembro de 2025  
**Versão:** 1.0
