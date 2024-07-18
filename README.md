# Desafio Backend: Lista de Tarefas 📚

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

API para gerenciar tarefas (CRUD) que faz parte [desse desafio](https://github.com/simplify-liferay/desafio-junior-backend-simplify) para pessoas desenvolvedoras backend júnior, que se candidatam para a Simplify.

## Descrição 🤔
- A aplicação deve consiste em um sistema de gerenciamento de tarefas, onde os usuários podem criar, visualizar, editar e excluir tarefas.

## Requisitos propostos pelo Desafio ✅
- Usar banco de dados
- Campos mínimos da entidade de tarefa
    - Nome
    - Descrição
    - Realizado
    - Prioridade
- Criar CRUD de tarefas

### Serviço RESTful 🚀

* Desenvolvimento de um serviço RESTful para toda a aplicação.

## Tecnologias 💻
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Spring Data + R2DBC](https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- [Docker](https://www.docker.com/)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [H2](https://www.baeldung.com/spring-boot-h2-database)
- [MySQL](https://www.mysql.com/)
- [JUnit5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [StepVerifier](https://www.baeldung.com/reactive-streams-step-verifier-test-publisher)
- [WebTestClient](https://docs.spring.io/spring-framework/reference/testing/webtestclient.html)
- [Jacoco](https://www.eclemma.org/jacoco/)
- [Bean Validation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)

## Práticas adotadas ✨

- SOLID, DRY, YAGNI, KISS
- API REST reativa na web e na camada de banco
- Uso de DTOs para a API
- Injeção de Dependências
- Testes Automatizados
- Tratamento de Exceções Personalizada
- Geração automática do Swagger com a OpenAPI 3

## Diferenciais 🔥

Alguns diferenciais que não foram solicitados no desafio:

* Programação Reativa
* Conteinerização da Aplicação
* TDD - Test Driven Development
* Testes de Cobertura com Jacoco
* Tratamento de exceções
* Testes Unitários e Teste de Integração
* Documentação Swagger

## Como executar 🎉

1.Clonar repositório git:

```text
git clone https://github.com/FernandoCanabarroAhnert/simplify-desafio-backend.git
```

2.Instalar dependências.

```text
mvn clean install
```

3.Executar a aplicação Spring Boot.

4.Testar endpoints através do Postman ou da url
<http://localhost:8080/swagger-ui/index.html#/>

### Usando Docker 🐳

- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Construir a imagem:
```
./mvnw spring-boot:build-image
```
- Executar o container:
```
docker run --name desafio-simplify -p 8080:8080  -d desafio-simplify:0.0.1-SNAPSHOT
```

## API Endpoints 📚

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/):

- Criar Tarefa 
```
$ http POST http://localhost:8080/todos

[
  {
    "nome": "Concluir o Desafio",
    "descricao": "Testar a Conexão com o Banco de Dados",
    "realizado": false,
    "prioridade": 1
  }
]
```

- Listar Tarefas
```
$ http GET http://localhost:8080/todos

[
  {
    "id": 1,
    "nome": "Concluir o Desafio",
    "descricao": "Testar a Conexão com o Banco de Dados",
    "realizado": false,
    "prioridade": 1
  }
]
```

- Consultar Tarefa pelo Id
```
$ http GET http://localhost:8080/todos/1

[
  {
    "id": 1,
    "nome": "Concluir o Desafio",
    "descricao": "Testar a Conexão com o Banco de Dados",
    "realizado": false,
    "prioridade": 1
  }
]
```

- Atualizar Tarefa
```
$ http PUT http://localhost:8080/todos/1

[
  {
    "id": 1,
    "nome": "Atualizar o Desafio",
    "descricao": "Testar a Conexão com o Docker,
    "realizado": true,
    "prioridade": 2
  }
]
```

- Remover Tarefa
```
http DELETE http://localhost:8080/todos/1

[ ]
```