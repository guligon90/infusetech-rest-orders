# Execução da plataforma - Web Service Java

Com o servidor de banco de dados devidamente construído e aceitando conexões, o web service, que consiste de uma aplicação Spring Boot, seguindo um padrão de projeto similar ao Model-View-Controller (MVC), poderá ser posto em funcionamento.

Ao contrário do serviço de banco de dados, a aplicação Java em questão pode ser testada tanto no contexto do Docker (rodando em container), quando localmente (executando o pacote JAR). Segue agora uma referência para cada caso.

## Via Docker Compose

Similarmente ao serviço `database`, a estrutura de declaração da imagem segue o padrão:

```bash
infra/
└── docker
    └── webservice
        ├── .webservice.env # Variáveis de ambiente a serem consumidas pelo container
        ├── Dockerfile # Manifesto da imagem (em estágios, para build e execução do JAR)
        └── .dockerignore # Listagem de itens a serem ignorados pelo Docker Compose durante a build
# ...
└── docker-compose.yml # Contém a declaração do serviço "webservice". Consome Dockerfile.
```

Similarmente, podemos prosseguir fazend a build da respectiva imagem:

```bash
make build c=webservice
```

Com a build finaliza, inicie o serviço e capture os logs de funcionamento através do comando:

```bash
make init c=webservice
```

Após isso, o container Docker deverá estar em plena execução, pronto para conexões HTTP na porta **8080**. Você pode se certificar disso listando os conteieneres em funcionamento:

```bash
make ps
```

## Localmente

Com o OpenJDK e Maven devidamente instalados, é possivel também fazer a criação do pacote JAR e execução do mesmo. Para isso, é necessária a publicação das variáveis de ambiente em um arquivo `.env`, na raíz do projeto, com os seguintes parâmetros:

```bash
DB_HOST=localhost # Ou 127.0.0.1
DB_NAME=orders_db # Padrão. Mesmo valor declarado em ./infra/docker/database/.database.env
DB_USER=[seu-usuario]
DB_PASSWORD=[sua-senha]
DB_PORT=3306 # Padrão. Mesmo valor declarado em ./infra/docker/database/.database.env
```

Como o `.env` criado, basta publicar as variáveis:

```bash
source .env
```

### Execução do web service

Em seguida, execute o comando que cria o JAR e executa o web service:

```bash
make runws
```

### Testes unitários (JUnit)

Similarmente, os testes unitários podem ser rodados de forma automatica através do comando:

```bash
make tests
```

---

## O que deseja fazer?

- [Voltar para raíz](../../README.md)
- [Execução da plataforma - Servidor MySQL](./DATABASE.md)
- [Execução da plataforma - API de Pedidos](./API-TESTING.md)
- [Especificações originais](./ORIGINAL-SPECS.md)
