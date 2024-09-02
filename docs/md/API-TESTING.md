# Execução da plataforma - API de Pedidos

O escopo do projeto consistiu na implementação de um web service, capaz de fornecer mecanismo para a manipulação de dados de pedidos, seguindo uma modelagem básica, i.e. sem a relação entre ítens e o seu respectivos produtos.
O mecanismo em questão se trata de uma API RESTful, servida por uma aplicação Java, implementada utilizando o framework Spring Boot (v3).

## Endpoints

Baseado nas especificações originais do projeto, foram construídos os seguintes endpoints:

- **URL base: <http://localhost:8080/api/order>**

| **Método**   | **Endpoint**     | **Descrição**                                                       | **Resposta primária**  |
| ------------ |  --------------- | ------------------------------------------------------------------- | ---------------------- |
| GET          | `/{id}`          | Retorna detalhes de um único pedido, dado um `id` válido.           | HTTP 200 OK            |
| PUT          | `/{id}`          | Atualiza um pedido já existente, dado um `id` válido.               | HTTP 200 OK            |
| DELETE       | `/{id}`          | Remove um pedido já existente, dado um `id` válido.                 | HTTP 204 NO_CONTENT    |
| POST         | `/`              | Cria um novo pedido, dada uma payload válida.                       | HTTP 201 CREATED       |
| POST         | `/bulk`          | Possibilita a criação de pedidos em lote, dada uma payload válida.  | HTTP 201 CREATED       |
| POST         | `/search`        | Realiza a listagem de pedidos, dado um conjunto válido de filtros.  | HTTP 200 OK            |

## Documentação

Um detalhamento maior da API, concernindo a especificação das estruturas associadas às requisições, assim como comportamentos adversos, pode ser conferido via a implementação da **Swagger UI**. Seguem abaixo os endpoints pertinentes:

- **URL base: <http://localhost:8080/api/docs>**

| **Método**   | **Endpoint**     | **Descrição**                                                               | **Resposta primária**  |
| ------------ |  --------------- | --------------------------------------------------------------------------  | ---------------------- |
| GET          | `/`              | Gera a Swagger UI, com a documentação interativa. Acessível via navegador. | HTTP 200 OK            |
| GET          | `/json/orders`   | Retorna a documentação da API estruturada em um JSON.                       | HTTP 200 OK            |

## Testes exploratórios

De modo a facilitar os testes de requisições à API, uma collection em formato JSON foi criada via **Postman** e exportada na raíz do projeto, em `./resources`, assim como o JSON contendo variáveis consumidas pela collection (environment). Seguem abaixo instruções para:

- [Instalação](https://www.postman.com/downloads/) do Postman;
- [Importação](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-and-exporting-overview/) da collection e enviroment.

---

## O que deseja fazer?

- [Voltar para raíz](../../README.md)
- [Execução da plataforma - Servidor MySQL](./DATABASE.md)
- [Execução da plataforma - Web Service Java](./WEBSERVICE.md)
- [Especificações originais](./ORIGINAL-SPECS.md)
