# 🏦 Sistema Bancário - Core API

Uma API RESTful desenvolvida para simular o núcleo de operações de um sistema bancário, focada em **consistência de dados, resolução de concorrência e modelagem de domínio profunda**.

Este projeto foi construído como um laboratório de Engenharia de Software para ir além do "CRUD genérico", explorando problemas reais de domínios financeiros, aplicando design patterns e garantindo a resiliência da aplicação.

## 🛠 Tecnologias Utilizadas

* **Java 21:** Uso de `records` para DTOs imutáveis.
* **Spring Boot 3.2:** Spring Web, Spring Data JPA.
* **PostgreSQL:** Banco de dados relacional robusto (Hospedado via Render).
* **Flyway:** Versionamento e migrations do banco de dados.
* **JUnit 5:** Testes unitários focados em TDD no núcleo de negócio.

---

## 🧠 Decisões Arquiteturais e Trade-offs

Abaixo, detalho as principais decisões tomadas durante o desenvolvimento para garantir a integridade do sistema:

### 1. Modelagem de Domínio Rica (Domain-Driven Design)
Foi evitado o anti-pattern de *Modelos Anêmicos*. A entidade `Conta` não possui métodos `setSaldo()`. Toda a mutação de estado ocorre através de métodos de negócio que protegem as invariantes (`depositar`, `sacar`, `transferir`).
* **Tipagem Financeira:** O tipo `Double` foi estritamente evitado devido a problemas de arredondamento em ponto flutuante. Toda a manipulação monetária utiliza `BigDecimal`.

### 2. Concorrência e o Problema do "Gasto Duplo" (Race Condition)
Em sistemas financeiros, duas transações simultâneas na mesma conta podem gerar inconsistências críticas.
* **A Solução:** Implementação de **Pessimistic Locking** no nível do banco de dados. O método do repositório utiliza `@Lock(LockModeType.PESSIMISTIC_WRITE)`, traduzindo-se para um `SELECT ... FOR UPDATE` no PostgreSQL.
* **Trade-off:** Escolhi o Lock Pessimista em vez do Otimista (`@Version`) porque, em transações financeiras, a consistência absoluta (ACID) é inegociável. O trade-off aceito foi o sacrifício de uma fração de *throughput* (vazão) em cenários de alta concorrência concorrendo pelo mesmo ID de conta, garantindo que o saldo nunca fique incorreto.

### 3. Persistência Profissional e Migrations
O uso de `spring.jpa.hibernate.ddl-auto=update` foi completamente descartado.
* O versionamento do banco de dados é gerido pelo **Flyway**, garantindo previsibilidade, rastreabilidade e segurança nos deploys. O Hibernate atua apenas no modo `validate` para garantir que as entidades Java estão em sincronia com os scripts SQL.

### 4. Contratos de API e Imutabilidade
* A exposição direta das entidades (`Conta`) para a camada web foi evitada para prevenir *Mass Assignment Vulnerabilities*.
* Utilizamos o padrão **DTO** através dos novos `records` do Java 21, que fornecem classes imutáveis e limpas de forma nativa, eliminando a dependência de bibliotecas de geração de código (como Lombok) para esta finalidade.

### 5. Tratamento Global de Erros (Observabilidade Limpa)
A API não retorna "Stack Traces" no formato 500 Internal Server Error para problemas de negócio.
* Um `@RestControllerAdvice` captura exceções específicas de domínio (ex: `SaldoInsuficienteException`, `ContaInexistenteException`) e as traduz para contratos JSON padronizados com os status HTTP semanticamente corretos (`422 Unprocessable Entity`, `404 Not Found`, `400 Bad Request`).

---

## 🚀 Como Executar o Projeto

1. Clone este repositório.
2. Certifique-se de ter o Java 21+ instalado.
3. O projeto está configurado para acessar um PostgreSQL provisionado na nuvem (Render) para fins de teste. Caso deseje rodar localmente, altere as variáveis no `application.properties` e suba o banco com Docker.
4. Execute a aplicação na sua IDE ou via terminal com o Maven.
5. O Flyway se encarregará de criar a estrutura do banco e injetar as contas de teste (`11111` com R$ 1000 e `22222` com R$ 500).

### Exemplo de Requisição (Transferência)

```http
POST /transferencia
Content-Type: application/json

{
  "idOrigem": "11111",
  "idDestino": "22222",
  "valor": 150.00
}