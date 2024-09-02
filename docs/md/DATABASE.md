# Execução da plataforma - Servidor MySQL

Segue agora uma referência rápida de como colocar a stack inteira em funcionamento, de modo à conduzir testes exploratórios da API.

Como foi mencionado anteriormente, o servidor MySQL está encapsulado em uma imagem Docker, construida localmente:

```bash
infra/
└── docker
    ├── app
    │   └── # ...
    └── database
        ├── .database.env # Variáveis de ambiente do MySQL (banco, usuário, ...)
        ├── Dockerfile # Manifesto da imagem (MySQL 9)
        └── init-db.sql # Script que cria o banco de dados
# ...
└── docker-compose.yml # Contém a declaração do serviço "database". Consome Dockerfile
```

## Servidor MySQL

Como o processo de build da imagem requer o argumento `$MYSQL_DATABASE`, essa variável de ambiente deve ser declarada antes do build. Para isso, basta executar no terminal, na raíz do projeto:

```bash
source ./infra/docker/database/.database.env

```

Em seguida, faça o build da imagem Docker:

```bash
make build c=database
```

Com a build finaliza, inicie o serviço e capture os logs de funcionamento através do comando:

```bash
make init c=database
```

Após isso, o container Docker deverá estar em plena execução, pronto para conexões TCP na porta **3306**. Você pode se certificar disso listando os conteieneres em funcionamento:

```bash
make ps
```

## Ferramenta de consulta - *phpMyAdmin*

De modo a conferir se os dados de pedidos estão sendo de fato alterados pela API REST, ou até mesmo para realizar intervenções diretamente no banco de dados, o serviço `phpmyadmin` foi criado para facilitar isso.
Analogamente ao serviço `database`, para inciar o phpMyAdmin, basta executar no terminal:

```bash
make init c=phpmyadmin
```

> [!IMPORTANT]  
> As variáveis de ambiente para este serviço estão declaradas em [./infra/docker/database/.phpmyadmin.env](../../infra/docker/database/.phpmyadmin.env).

---

## O que deseja fazer?

- [Voltar para raíz](../../README.md)
- [Execução da plataforma - Web Service Java](./WEBSERVICE.md)
- [Execução da plataforma - API de Pedidos](./API-TESTING.md)
- [Especificações originais](./ORIGINAL-SPECS.md)
