# Sistema MonGe - UFPR

Sistema de exemplo desenvolvido para a disciplina de **Engenharia de Software** da Universidade Federal do Paraná (UFPR).

O projeto demonstra a criação de uma **API REST** com **Spring Boot** e um **frontend web** simples consumindo a API.

## Como Executar

### Pré-requisitos

- **Java 17+**
- **Maven 3.6+**
- **Python 3** ou **Node.js** (para servidor web)

### Backend (API REST)

A API é desenvolvida em **Spring Boot 3.1.5** com **Maven**.

Navegue para a pasta da API
> cd monge-api

Compilar apenas
> mvn compile

Compilar e executar
> mvn spring-boot:run

A API estará disponível em: [**http://localhost:8080**](http://localhost:8080)

#### Endpoints Disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/v1/hello/{nome}` | Saudação personalizada |
| `GET` | `/api/v1/status` | Status do sistema |

**Exemplos:**
- http://localhost:8080/api/v1/hello/João
- http://localhost:8080/api/v1/status

### Frontend (Interface Web)

O frontend é desenvolvido em **HTML5**, **CSS3** e **JavaScript** com **Axios**.

Navegue para a pasta web
> cd monge-web

#### Opção 1: Servidor Python
> python3 -m http.server 3000


#### Opção 2: Servidor http-server
> npx http-server . -p 3000