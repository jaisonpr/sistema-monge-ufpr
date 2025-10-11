Este documento apresenta a arquitetura do sistema de monitoramento e gestão de bolsistas com foco nos protocolos de comunicação, interfaces e fluxos de dados, abstraindo explicitamente detalhes específicos de linguagens de programação e tecnologias de implementação.

O objetivo desta abordagem é permitir flexibilidade tecnológica, garantindo que as decisões arquiteturais priorizem padrões de interoperabilidade, segurança e escalabilidade, sem atrelamento a plataformas ou ferramentas específicas.

As definições de protocolos (por exemplo, REST sobre HTTPS, OAuth2 para autenticação) facilitam a compreensão da arquitetura em um nível conceitual, promovendo independência tecnológica e potencial para evolução futura conforme demandas institucionais ou avanços tecnológicos.

Qualquer detalhamento sobre ferramentas, linguagens ou frameworks específicos será abordado em documentos técnicos separados, complementares a este artefato.


















![Backend Component Diagram](backend.jpg)


O diagrama de componentes do backend Spring Boot evidencia as principais relações entre os módulos:

- **Controller depende de Service:** responsável por receber requisições HTTP, validar dados e delegar ao serviço a execução das regras de negócio.
- **Service depende de Repository:** orquestra as regras de negócio e interage com os dados através dos repositórios.
- **Service e Repository dependem de Model:** ambos lidam diretamente com as entidades do domínio da aplicação.
- **Encapsulamento:** o Controller não expõe nem acessa diretamente o repositório, mantendo a separação entre regra de negócio e acesso a dados.

No fluxo, o Controller expõe endpoints REST e repassa as requisições para a camada Service, que concentra as regras de negócio e coordena chamadas para o Repository. O Repository realiza o acesso aos dados (CRUD) e manipula objetos do Model, que representa as entidades e estruturas de dados do domínio.

Essa arquitetura segue o padrão recomendado para aplicações Spring Boot, alinhada aos princípios de DDD e separação de responsabilidades, promovendo baixa acoplamento, facilitando manutenção, testes e evolução ao longo do ciclo de vida do sistema.
