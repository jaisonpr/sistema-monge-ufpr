# Relatório Integrado de Auditoria Técnica – Projeto MonGe

**Sistema:** Monitoramento e Gestão de Bolsistas em Projetos Acadêmicos da UFPR[^1][^2]
**Versão Analisada:** MVP (Frontend JS/HTML/CSS `monge-web` + Backend Java REST `monge-api`)[^2]
**Data da Auditoria:** 20/11/2025[^1][^2]
**Responsável Técnico:** Analista de Qualidade[^2][^1]



## 1. Sumário executivo

A auditoria integrada avaliou a documentação de requisitos/arquitetura, o código-fonte (via CLOC e SonarQube) e aspectos não funcionais do frontend (via Lighthouse desktop).[^3][^1][^2]
O sistema MonGe encontra-se adequado como **MVP funcional**, com boa base de especificação e backend tecnicamente saudável, porém com lacunas críticas em **planejamento de testes** e **qualidade estrutural do frontend**, especialmente em acessibilidade e modularidade.[^3][^1][^2]

**Veredito:** apto para uso acadêmico e laboratório, mas **não recomendado para implantação institucional plena** até que sejam implementados: (i) plano de testes e cenários formais, (ii) reforço de logging/auditoria no backend e (iii) refatoração do frontend visando acessibilidade e manutenibilidade.[^4][^1][^2]



## 2. Visão integrada: documentação, código e RNF

A documentação cobre bem o “dever-ser” do sistema (DV, RF/RNF, HUs, RNs, arquitetura, DER, casos de uso, TAP e cronograma), oferecendo rastreabilidade HU ↔ RF ↔ RN.[^1]
Por outro lado, a ponte entre essa especificação e a verificação de qualidade ainda não foi construída: não há plano de testes, cenários funcionais, testes de RNF nem metas de cobertura formalizadas.[^1]

No código, a análise volumétrica mostra backend enxuto (805 SLOC em 30 arquivos Java) e frontend maior (1.157 SLOC em 4 arquivos, com 545 linhas em `app.js`), refletindo boa granularidade na API e centralização excessiva na camada web.[^5][^2]
O SonarQube evidencia uma densidade de issues moderada no backend (≈ 28/KLOC) e elevada no frontend (≈ 52,7/KLOC), com ênfase em _code smells_ de acessibilidade e organização de JavaScript.[^6][^7][^2]

A auditoria Lighthouse desktop em `http://localhost:3000/` confirma excelente desempenho (Performance 99), boas práticas (96) e SEO (90), porém acessibilidade apenas razoável (83), alinhada às violações de eMAG/WCAG apontadas pelo SonarQube.[^7][^2][^3]



## 3. Documentação e planejamento de testes

### 3.1 Pontos fortes

A base documental inclui Documento de Visão, RF/RNF, HUs do épico de aprovação/feedback, RNs, matriz HU ↔ RF ↔ RN, Documento de Arquitetura, DER, diagramas de componentes/implantação/casos de uso, TAP, cronograma e mapa fase × artefato.[^1]
Esses artefatos estão coesos, sem ambiguidades graves, e explicitam critérios de aceitação nas HUs, o que é ideal para derivar casos de teste claros e rastreáveis.[^1]

### 3.2 Lacunas críticas

Não existe **Plano de Testes** documentado, descrevendo estratégia, níveis (unidade, integração, sistema, aceitação), critérios de entrada/saída, responsáveis e priorização por risco.[^1]
Também não há **cenários de teste funcionais** por HU/RF nem casos de teste derivados dos RNF (desempenho, segurança, usabilidade, interoperabilidade, manutenibilidade), deixando qualidades críticas sem verificação planejada.[^1]

A matriz HU ↔ RF ↔ RN não foi expandida para incluir o eixo de testes, o que impede afirmar quais requisitos possuem casos de teste associados e quais estão descobertos.[^1]
Faltam ainda diretrizes formais de teste unitário (metas de cobertura, ferramentas e critérios de aceite em pull requests) e um checklist de revisão de requisitos/modelos para uso de QA antes da codificação.[^1]

### 3.3 Consequências

Sem esse conjunto mínimo de artefatos de teste, a equipe depende de conhecimento tácito para validar funcionalidades, com risco de cobertura irregular e de regressões silenciosas em fluxos críticos (ex.: aprovação de lançamentos, prazos semanais, relatórios).[^1]
Além disso, RNF como tempo de resposta, capacidade de carga, segurança OAuth2/OIDC e aderência ao eMAG ficam sujeitos a avaliação informal, o que não é compatível com o grau de responsabilidade institucional do sistema.[^4][^1]



## 4. Qualidade de código – backend `monge-api`

O backend apresenta estrutura compatível com uma arquitetura em camadas, com distribuição equilibrada das 805 linhas de código por 30 arquivos Java, o que sugere boa granularidade e aderência básica a SRP.[^5][^2]
A análise estática (SonarQube) apontou sobretudo _code smells_ de manutenibilidade e alguns problemas que afetam testabilidade e observabilidade, mas sem grande incidência de bugs ou vulnerabilidades graves.[^6][^2]

As principais questões são: uso de `System.out`/`System.err` para logging (regra S106), _field injection_ com `@Autowired` (S6813), imports não utilizados (S1128), duplicação de literais (S1192) e tratamento genérico de exceções.[^8][^2][^6]
Essas práticas atrapalham a criação de testes unitários isolados e comprometem requisitos de auditoria, especialmente RN014 (histórico de lançamentos e trilhas de aprovação) e controles de segurança ligados a RF006.[^2][^4][^6]

Em termos de risco, a dívida técnica do backend é considerada **baixa a moderada** e concentrada em ajustes de configuração, refino de logging e injeção de dependências, sem necessidade de reescrever a arquitetura de serviços.[^6][^2]



## 5. Qualidade de código – frontend `monge-web` e Lighthouse

O frontend concentra 1.157 linhas de código em apenas quatro arquivos, sendo 545 em `app.js`, que centraliza responsabilidades de acesso à API, manipulação de DOM e validações, configurando um “God Script”.[^5][^2]
Essa concentração aumenta a complexidade cognitiva de funções JavaScript, dificultando leitura, testes e evolução incremental do código.[^9][^2]

O SonarQube aponta densidade de issues significativamente maior no frontend (≈ 52,7/KLOC), com foco em acessibilidade (uso incorreto de `<a>` como botão, elementos interativos sem suporte a teclado, contraste inadequado), más práticas de escopo (`var` globais) e uso de `console.log` em produção.[^7][^9][^2]
Esses problemas impactam diretamente RNF de acessibilidade (eMAG/WCAG), usabilidade e manutenibilidade, tornando o frontend o principal vetor de risco técnico do MVP.[^4][^7][^2]

A auditoria **Lighthouse 13.0.1** em `http://localhost:3000/` (perfil desktop) mostrou scores de **Performance 99**, **Accessibility 83**, **Best Practices 96** e **SEO 90**, com métricas de carregamento em torno de 0,5 s.[^3][^2]
Isso confirma que, embora a experiência de desempenho e boas práticas seja excelente em ambiente local, a acessibilidade ainda está aquém da meta recomendada (≥ 90) para um sistema público, refletindo exatamente as violações já mapeadas pelo SonarQube.[^9][^2][^3]



## 6. Síntese de riscos e impacto institucional

Do ponto de vista de governança de TI da UFPR, os principais riscos identificados são:[^2][^4][^1]

- **Planejamento de testes inexistente:** risco alto de bugs funcionais e de RNF passarem despercebidos, especialmente em fluxos de aprovação de lançamentos e geração de relatórios que impactam pagamento de bolsas.[^1]
- **Acessibilidade parcial (Lighthouse 83 e issues Web/CSS):** risco de descumprimento de eMAG/WCAG e dificuldade de uso por pessoas com deficiência, o que tem impacto institucional e jurídico.[^7][^3][^2]
- **Observabilidade insuficiente no backend (logging simples):** risco de não conseguir reconstruir a trilha de decisões em caso de auditoria institucional ou questionamento de bolsistas/orientadores.[^4][^6][^2]
- **Manutenibilidade limitada do frontend:** arquitetura JS monolítica aumenta o custo de evolução e a probabilidade de regressões quando novas HUs forem implementadas.[^9][^5][^2]

Apesar desses riscos, a combinação de documentação forte, backend coeso e boa performance de frontend coloca o projeto em posição favorável para atingir um patamar institucional com uma rodada focada de mitigação.[^3][^2][^1]



## 7. Roadmap integrado de mitigação

### 7.1 Eixo documentação e QA

- Elaborar um **Plano de Testes** formal, descrevendo níveis, tipos de teste por camada, critérios de entrada/saída, responsabilidades e priorização de riscos, tomando como base RF/RNF e a matriz HU ↔ RF ↔ RN.[^1]
- Produzir **cenários funcionais** para todas as HUs (começando pelo épico de aprovação/feedback), com pré-condições, passos, dados e resultados esperados, e amarrá-los à matriz de rastreabilidade.[^1]
- Definir **testes de RNF** (desempenho, segurança, acessibilidade, interoperabilidade, manutenibilidade) com métricas objetivas e ferramentas de apoio (Lighthouse, Pa11y/ASES, JMeter, ZAP).[^3][^1]


### 7.2 Eixo backend

- Migrar de `System.out`/`System.err` para **logging estruturado** com SLF4J/Logback, garantindo logs por ID de lançamento, usuário e transição de status para cumprir RN014.[^6][^2][^4]
- Substituir _field injection_ por **injeção via construtor**, reduzindo acoplamento ao container Spring e facilitando criação de testes unitários para regras de negócio críticas.[^8][^2][^6]
- Consolidar mensagens e códigos de erro em constantes centralizadas, permitindo padrão consistente de respostas e futura internacionalização.[^2][^6]


### 7.3 Eixo frontend

- Refatorar `app.js` em módulos (serviços REST, camada de apresentação, utilidades), reduzindo complexidade e preparando o terreno para adoção gradual de um framework SPA, se desejado.[^5][^9][^2]
- Implementar um **plano de acessibilidade**: corrigir semântica de elementos interativos, garantir suporte a teclado, ajustar contraste de cores e revisar atributos `aria-*`, visando elevar o score Lighthouse de Accessibility de 83 para ≥ 90.[^7][^2][^3]
- Remover `console.log` de produção, eliminar variáveis globais e códigos mortos, e adotar padrões modernos de JS (`let`/`const`, `for…of`, `Number.parseInt`), aumentando robustez e legibilidade.[^9][^7][^2]


### 7.4 Qualidade contínua

- Definir metas de **cobertura de testes** por módulo (por exemplo, 80% para serviços de negócio do backend, 60% para camadas de UI críticas) e usar SonarQube para monitorar esses índices em cada build.[^6][^2][^1]
- Integrar SonarQube e Lighthouse como **gates de qualidade** na pipeline de CI/CD, bloqueando merges que introduzam novos problemas críticos ou que façam cair scores abaixo dos limiares acordados (ex.: Accessibility < 90, nova issue de severidade alta não justificada).[^2][^3][^6]



## 8. Conclusão integrada

O projeto MonGe apresenta um quadro típico de MVP bem conduzido: documentação conceitual robusta, arquitetura clara, backend organizado e frontend funcional, porém com pontos sensíveis em acessibilidade e teste que ainda o separam de um sistema institucional plenamente auditável.[^3][^2][^1]
A execução disciplinada do roadmap proposto — começando por planejamento de testes, endurecimento do backend para auditoria e refatoração incremental do frontend — é suficiente para transformar o estado atual em uma base sólida para operação oficial na UFPR, mantendo a boa performance já demonstrada e reduzindo significativamente o risco técnico e institucional.[^4][^2][^3][^1]


[^1]: relatorio_documentacao.md

[^2]: relatorio_codigo.md

[^3]: lighthouse-desktop.json

[^4]: RF-e-RNF.md

[^5]: cloc.md

[^6]: monge_ALL_issues.csv

[^7]: monge_web_ALL_issues.csv

[^8]: monge_ALL_issues.json

[^9]: monge_web_ALL_issues.json

