# Fintech Backend

Projeto de backend em Spring Boot para a Fintech.

## Tecnologias Utilizadas

*   Java 21
*   Spring Boot 3.5.7
*   Spring Data JPA
*   Spring Web
*   Spring Boot Devtools
*   Lombok
*   Oracle JDBC Driver (ojdbc11)
*   Springdoc OpenAPI (Swagger UI)
*   Maven

## Pré-requisitos

*   JDK 21
*   Maven
*   Acesso ao Oracle Database da FIAP

## Como Executar Localmente

1.  **Clone o repositório:**
    ```bash
    git clone <url-do-repositorio>
    cd fintech-backend
    ```

2.  **Configuração do Banco de Dados:**

    O projeto está configurado para usar o banco de dados Oracle da FIAP. O arquivo de configuração é o `src/main/resources/application.yaml` e já está preenchido com as credenciais necessárias. Certifique-se de que você tem acesso à rede da FIAP.

    ```yaml
    spring:
      datasource:
        url: jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
        username: RM565891
        password: 150998
      jpa:
        show-sql: true
        properties.hibernate.default_schema: RM565891
    ```

3.  **Execute a aplicação:**

    Você pode executar a aplicação usando o Maven Wrapper:

    ```bash
    ./mvnw spring-boot:run
    ```

    A aplicação estará disponível em `http://localhost:8080`.

4.  **Documentação da API (Swagger):**

    A documentação da API está disponível em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
