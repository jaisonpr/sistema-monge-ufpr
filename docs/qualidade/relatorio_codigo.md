# Relatório de Qualidade de Código – Projeto MonGe

**Sistema:** Monitoramento e Gestão de Bolsistas em Projetos Acadêmicos da UFPR  
**Versão Analisada:** MVP (Backend Java REST + Frontend Web estático)  
**Data da Análise:** 20/11/2025  
**Papel:** Analista de Qualidade  

---

## 1. Objetivo e Escopo

Este relatório consolida a análise de qualidade de código do MonGe a partir de três fontes principais: contagem de linhas (CLOC), inspeções estáticas pelo SonarQube (backend `monge-api` e frontend `monge-web`) e auditoria não funcional com Lighthouse (desktop). [attached_file:64][attached_file:34][attached_file:35][attached_file:31][attached_file:160]  
O objetivo é identificar riscos técnicos, pontos fortes e oportunidades de melhoria alinhadas aos requisitos funcionais e não funcionais do sistema, preparando o terreno para critérios de aceite em ambiente institucional. [attached_file:64][attached_file:5][attached_file:65]  

---

## 2. Visão Geral Volumétrica (CLOC)

A contagem de linhas de código permite dimensionar o porte do MVP e comparar a “massa de código” entre backend e frontend. [attached_file:34][attached_file:64]  

| Componente   | Linguagens                    | Arquivos | Linhas de Código (SLOC) | Observação                                                                 |
| :----------- | :--------------------------- | :------- | :----------------------- | :------------------------------------------------------------------------- |
| monge-api    | Java                         | 30       | 805                     | Backend REST enxuto, com classes pequenas e boa granularidade.            |
| monge-web    | JavaScript, HTML, CSS, Text  | 4        | 1.157                   | Frontend concentrado em poucos arquivos, com forte acoplamento em `app.js`.|

[attached_file:34]

No backend, as 805 linhas Java distribuídas em 30 arquivos sugerem uma granularidade razoável de classes e aderência básica a princípios de responsabilidade única. [attached_file:34][attached_file:64]  
No frontend, 1.157 linhas em apenas quatro arquivos, sendo 545 em JavaScript, 344 em HTML e 262 em CSS, caracterizam um script central volumoso e potencialmente difícil de testar e evoluir. [attached_file:34][attached_file:64]  

---

## 3. Saúde de Código – Backend `monge-api`

### 3.1 Perfil de issues (SonarQube)

A análise do SonarQube sobre o módulo `monge-api` mostra predominantemente _code smells_ de manutenibilidade, além de alguns pontos de design que afetam testabilidade e observabilidade. [attached_file:35][attached_file:32][attached_file:64]  

Principais padrões encontrados:

- **Imports não utilizados (`java:S1128`)** em `OrientadorLancamentoController` e outras classes indicam código residual não limpo após refatorações. [attached_file:32][attached_file:35]  
- **Duplicação de literais (`java:S1192`)** em mensagens como “Lançamento não encontrado” aumenta o risco de inconsistências e dificulta internacionalização. [attached_file:32][attached_file:35]  
- **Field injection com `@Autowired` (`java:S6813`)** em controladores e serviços dificulta testes unitários e vai contra boas práticas de injeção via construtor no Spring. [attached_file:32][attached_file:35][attached_file:64]  
- **Uso de `System.out`/`System.err` para logging (`java:S106`)** prejudica observabilidade, integração com agregadores de log e auditoria institucional. [attached_file:35][attached_file:64]  

A densidade de issues no backend é moderada (≈ 28 issues/KLOC), sugerindo uma dívida técnica administrável se tratada ainda na fase de MVP. [attached_file:35][attached_file:64]  

### 3.2 Impacto arquitetural e de negócio

O uso de field injection reduz o isolamento entre componentes e dificulta a criação de testes automatizados para regras críticas, como aprovação de lançamentos e verificação de prazos semanais. [attached_file:32][attached_file:35][attached_file:64]  
O logging via saída padrão compromete requisitos de auditoria (por exemplo, RN014, que exige rastreio confiável de quem aprovou ou rejeitou lançamentos), pois não há logs estruturados por transação. [attached_file:5][attached_file:35][attached_file:64]  

---

## 4. Saúde de Código – Frontend `monge-web`

### 4.1 Perfil de issues (SonarQube)

O módulo `monge-web` concentra a maior parte da dívida técnica, com densidade de issues em torno de 52,7/KLOC, quase o dobro do backend. [attached_file:31][attached_file:33][attached_file:64]  

Principais padrões observados:

- **God Script em `app.js`**: centraliza chamadas REST, manipulação de DOM e validações, elevando a complexidade cognitiva (regra `javascript:S3776`). [attached_file:33][attached_file:64]  
- **Acessibilidade HTML (regras `Web:S6844`, `Web:S6848`, `MouseEventWithoutKeyboardEquivalentCheck`)**: uso de `<a>` como botão, elementos interativos não nativos e eventos apenas de mouse, prejudicando navegação por teclado e leitores de tela. [attached_file:31][attached_file:33]  
- **Contraste insuficiente (`css:S7924`)** em estilos CSS viola recomendações WCAG/eMAG. [attached_file:31][attached_file:33]  
- **Uso de `var` em escopo global (`javascript:S3504`)** e presença de variáveis/atribuições não utilizadas (`javascript:S1481`, `S1854`) indicam poluição de escopo e código morto. [attached_file:31][attached_file:33][attached_file:64]  
- **Chamadas a `console.log` em código de produção (`javascript:S1444`)** representam má higiene de logging e risco de exposição de dados de fluxo no console do usuário. [attached_file:31][attached_file:33]  

### 4.2 Relação com requisitos não funcionais

Os problemas de acessibilidade conflitam com requisitos de aderência ao eMAG e às diretrizes WCAG 2.1 definidas nos RNF do projeto. [attached_file:5][attached_file:31][attached_file:33]  
A estrutura monolítica de `app.js` colide com o RNF de manutenibilidade, aumentando o custo de evolução e o risco de regressões em futuros incrementos. [attached_file:5][attached_file:34][attached_file:64]  

---

## 5. Avaliação Automatizada com Lighthouse (Desktop)

### 5.1 Scores globais

Foi executada auditoria com **Lighthouse 13.0.1** em modo *navigation* (perfil desktop) sobre a URL `http://localhost:3000/`, gerando o artefato `lighthouse-desktop.json`. [attached_file:160]  

Os scores obtidos (0–100) foram:

| Categoria       | Score | Interpretação |
| :------------- | :---: | :------------ |
| Performance    |  99   | Carregamento extremamente rápido em desktop (FCP/LCP/Speed Index ≈ 0,5 s). |
| Accessibility  |  83   | Acessibilidade boa, porém ainda abaixo da zona “verde” (≥ 90) esperada para sistema institucional. |
| Best Practices |  96   | Forte aderência a boas práticas de segurança/UX. |
| SEO            |  90   | Configuração adequada para descoberta básica e indexação. |

[attached_file:160]

Os campos `categories.performance.score = 0.99`, `accessibility.score = 0.83`, `best-practices.score = 0.96` e `seo.score = 0.9` confirmam o excelente desempenho da aplicação em ambiente local, com acessibilidade sendo o principal eixo de melhoria. [attached_file:160]  
As métricas de tempo de carregamento (`first-contentful-paint`, `largest-contentful-paint` e `speed-index`) registram valores próximos de 0,5 s, explicando o score de 99 em Performance. [attached_file:160]  

### 5.2 Convergência com achados do SonarQube

Os problemas de acessibilidade apontados pelo Lighthouse (como contraste insuficiente e elementos interativos sem suporte pleno a teclado) convergem com as issues Web/CSS do SonarQube. [attached_file:33][attached_file:160]  
Essa convergência reforça a necessidade de um plano de ação específico para acessibilidade, incluindo ajuste de contraste, correção de elementos semânticos e melhoria de navegação por teclado, em linha com eMAG/WCAG. [attached_file:5][attached_file:64][attached_file:160]  

---

## 6. Matriz de Risco: Código vs. Requisitos

A tabela a seguir sintetiza como os achados de código (SonarQube + Lighthouse) impactam diretamente requisitos de negócio e não funcionais do MonGe. [attached_file:64][attached_file:35][attached_file:31][attached_file:160]  

| Requisito / RN                       | Achado Técnico                                                        | Impacto no Negócio                                                                                     | Risco |
| :----------------------------------- | :-------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------- | :---- |
| RN014 – Histórico e auditoria        | Logs via `System.out`/`System.err`; ausência de logging estruturado.  | Dificulta rastrear aprovações/reprovações de lançamentos em auditorias internas e externas.           | Alto  |
| RN011 – Prazos semanais de envio     | Validações concentradas no frontend em JS complexo.                   | Risco de burlas de prazo se o backend não reforçar regras de data/estado.                             | Alto  |
| RF006 – Segurança institucional      | Injeção por campo e baixa testabilidade de controladores.             | Dificulta garantir via testes automatizados que apenas perfis autorizados acessem APIs críticas.      | Médio |
| RNF006 – Acessibilidade (eMAG/WCAG)  | Issues de acessibilidade em HTML/CSS + score 83 em Lighthouse.        | Pode inviabilizar o uso por pessoas com deficiência e descumprir diretrizes de acessibilidade digital.| Alto  |
| RNF005 – Manutenibilidade            | `app.js` monolítico com complexidade alta e variáveis globais.        | Aumenta custo de evolução, risco de regressão e dificuldade de modularização futura.                  | Médio |

[attached_file:5][attached_file:35][attached_file:31][attached_file:160][attached_file:64]

---

## 7. Recomendações Prioritárias

### 7.1 Backend `monge-api`

- Substituir `System.out`/`System.err` por **framework de logging estruturado** (SLF4J + Logback), com correlação por ID de lançamento, usuário e ação, atendendo exigências de RN014. [attached_file:35][attached_file:64]  
- Migrar de **field injection** para **injeção via construtor**, facilitando criação de testes unitários isolados e reduzindo acoplamento ao container Spring. [attached_file:35][attached_file:32][attached_file:64]  
- Eliminar imports e literais duplicados, concentrando mensagens em constantes e facilitando internacionalização e padronização de respostas de erro. [attached_file:32][attached_file:35]  

### 7.2 Frontend `monge-web`

- Refatorar `app.js` em módulos menores (serviços de API, componentes de UI, utilitários de validação), reduzindo complexidade cognitiva e preparando o código para adoção futura de frameworks SPA. [attached_file:34][attached_file:33][attached_file:64]  
- Tratar de forma coordenada issues de acessibilidade apontadas por SonarQube e Lighthouse (semântica de botões, foco de teclado, contraste, uso de `aria-*`), visando elevar o score de Accessibility para ≥ 90. [attached_file:31][attached_file:33][attached_file:160]  
- Remover `console.log` de produção, substituir por logging controlado e modernizar o código JS (uso de `let`/`const`, `for…of`, `Number.parseInt`, etc.), melhorando manutenibilidade e higiene. [attached_file:31][attached_file:33][attached_file:64]  

### 7.3 Qualidade transversal e automação

- Definir metas de **cobertura de testes** (ex.: 80% serviços de negócio do backend, suíte mínima de testes de UI para fluxos principais no frontend) e acompanhar esses indicadores em cada release. [attached_file:64][attached_file:35]  
- Integrar **SonarQube e Lighthouse** como _quality gates_ na pipeline de CI/CD, bloqueando merges que introduzam novos problemas críticos ou reduzam scores abaixo de limiares acordados (por exemplo, Accessibility < 90). [attached_file:35][attached_file:31][attached_file:160]  

---

## 8. Conclusão

O código do MonGe apresenta um backend bem estruturado, com dívida técnica tratável focada em observabilidade e testabilidade, e um frontend funcional, porém com dívida estrutural relevante em acessibilidade e organização arquitetural. [attached_file:64][attached_file:35][attached_file:31]  
A combinação das evidências de SonarQube, CLOC e Lighthouse reforça que a prioridade imediata deve ser a refatoração do frontend e a implantação de boas práticas de logging e testes no backend, preparando o sistema para atender plenamente aos requisitos institucionais da UFPR. [attached_file:34][attached_file:35][attached_file:31][attached_file:160]

