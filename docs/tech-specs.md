# Vaga Programador Java

## 1. Objetivo

Criar uma solução java em formato de API que atenda aos seguintes requisitos para a recepção de pedidos dos clientes:

## 2. API de pedidos - Especificações dos casos de uso

### 2,1. Submissão de dados

#### 2.1.1. Estrutura do pedido

Criar um serviço que receba pedidos no formato xml e json com 6 campos:

- número controle - número aleatório informado pelo cliente.
- data cadastro (opcional)
- nome - nome do produto
- valor - valor monetário unitário produto
- quantidade (opcional) - quantidade de produtos.
- codigo cliente - identificação numérica do cliente.

#### 2.1.2. Critérios de aceitação

Critérios aceitação e manipulação do arquivo:

- O arquivo pode conter 1 ou mais pedidos, limitado a 10.
- Não poderá aceitar um número de controle já cadastrado.
- Caso a data de cadastro não seja enviada o sistema deve assumir a data atual.
- Caso a quantidade não seja enviada considerar 1.
- Caso a quantidade seja maior que 5 aplicar 5% de desconto no valor total, para quantidades a partir de 10 aplicar 10% de desconto no valor total.
- O sistema deve calcular e gravar o valor total do pedido.
- Assumir que já existe 10 clientes cadastrados, com códigos de 1 a 10.

### 2.2. Consulta de dados

Criar um serviço onde possa consultar os pedidos enviados pelos clientes.

#### 2.2.1. Estrutura dos filtros de pedidoa

- número pedido
- data cadastro
- todos

#### 2.2.2. Critérios aceitação

O retorno deve trazer todos os dados do pedido.

## 3. Frameworks

Fica a critério do candidato utilizar ou não, sem restrições de escolha.

## 4. Desejável

- Injeção de dependência
- Padrões de projeto
- Testes unitários

## 5. Obrigatório

- banco de dados mysql
- utilizar framework ORM
- utilizar a linguagem java 1.8 ou versão mais recente

## 6. Publicação

a solução deve ser publicada no github juntamente com o script de criação das tabelas.
deve ser enviado o link do repositório da solução para este email.

Aguardo confirmação de recebimento do e-mail!
