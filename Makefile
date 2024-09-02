# Diretório-raíz
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Cores
GREEN 	:= $(shell tput -Txterm setaf 2)
WHITE 	:= $(shell tput -Txterm setaf 7)
YELLOW	:= $(shell tput -Txterm setaf 3)
RED 	:= $(shell tput -Txterm setaf 1)
RESET 	:= $(shell tput -Txterm sgr0)

# Docker Compose
DOCKER_COMPOSE_CMD := docker compose
DOCKER_COMPOSE_FILE := docker-compose.yml
DOCKER_COMPOSE_FILE_PATH := $(ROOT_DIR)/$(DOCKER_COMPOSE_FILE)
COMPOSE_FILE_CMD := $(DOCKER_COMPOSE_CMD) -f $(DOCKER_COMPOSE_FILE_PATH)

# Constrói a documentação de cada script, visualizável via 'make' ou 'make help'
# A documentação dee cada script é feita através de uma string começando por '\#\#'
# Uma categoria de comandos pode ser adicionada em uma string iniciando a mesma com @category
HELP_FUN = \
    %help; \
    while(<>) { \
		push @{$$help{$$2 // 'Comandos'}}, [$$1, $$3] if /^([a-zA-Z\-]+)\s*:.*\#\#(?:@([a-zA-Z\-]+))?\s(.*)$$/ }; \
	    print "Utilização: make [comando]\n\n"; \
		for (sort keys %help) { \
    		print "${WHITE}$$_:${RESET}\n"; \
    		for (@{$$help{$$_}}) { \
    			$$sep = " " x (32 - length $$_->[0]); \
    			print "  ${YELLOW}$$_->[0]${RESET}$$sep${GREEN}$$_->[1]${RESET}\n"; \
    		}; \
		print "\n"; \
	}

.PHONY: help \
	build \
	confirm \
	clean \
	destroy \
	logs \
	restart \
	start \
	init \
	status \
	stop \
	up \
	run \
	enter \
	ps \
	topology \
	runws \
	tests

.DEFAULT_GOAL := help

info: header

define HEADER
+------------------------------------------------------------------------------------------------+
   ____     ___           ______        __           _      ______  ___         ___    __       
  /  _/__  / _/_ _____ __/_  __/__ ____/ /    ____  | | /| / / __/ / _ \___ ___/ (_)__/ /__  ___
 _/ // _ \/ _/ // (_-</ -_) / / -_) __/ _ \  /___/  | |/ |/ /\ \  / ___/ -_) _  / / _  / _ \(_-<
/___/_//_/_/ \_,_/___/\__/_/  \__/\__/_//_/         |__/|__/___/ /_/   \__/\_,_/_/\_,_/\___/___/
+------------------------------------------------------------------------------------------------+
endef
export HEADER

help: ##@Outros Mostra esta documentação.
	clear
	@echo "$$HEADER"
	@perl -e '$(HELP_FUN)' $(MAKEFILE_LIST)

build: ## Realiza a build de todas as imagens Docker, ou para um c=<node de serviço> específico
	@$(COMPOSE_FILE_CMD) build $(c)

confirm:
	@( read -p "$(RED)Tem certeza? [y/N]$(RESET): " sure && case "$$sure" in [sSyY]) true;; *) false;; esac )

clean: confirm ## Realiza a limpeza de todos os dados associados aos conteineres
	@$(COMPOSE_FILE_CMD) down

destroy: confirm ## Remove todas as imagens, volumes, networks e conteineres não utilizados. Use com cautela!
	@docker system prune --all --volumes --force
	@docker volume prune --all --force
	@docker network prune --force
	@docker image prune --all --force

logs: ## Adiciona captura de logs para todos os conteineres ou para um c=<nome de serviço>
	@$(COMPOSE_FILE_CMD) logs --follow $(c)

restart: ## Reinicia todos os conteineres ou apenas um c=<nome de serviço>
	@$(COMPOSE_FILE_CMD) stop $(c)
	@make init c=$(c)

start: ## Inicia todos os conteineres em background (detached mode) ou apenas um c=<nome de serviço>
	@$(COMPOSE_FILE_CMD) up -d $(c)

init: ## Inicia um conteiner em detached mode, com captura de logs
	@make start c=$(c) && make logs c=$(c)

status: ## Lista os status dos conteineres em execução
	@$(COMPOSE_FILE_CMD) ps

stop: ## Encerra a execução de todos os conteineres ou de apenas um c=<nome de serviço>
	@$(COMPOSE_FILE_CMD) stop $(c)

up: ## Inicia todos os conteineres em modo "attached" ou apenas um c=<nome de ser> containers in foreground
	@$(COMPOSE_FILE_CMD) up $(c)

run: ## Run cmdcommand or entrypoint for a c=<name> container
	@$(COMPOSE_FILE_CMD) run --rm $(c) $(cmd)

enter: ## Executa um prompt de comando dentro de um container, dado um c=<nome de serviço> e um b=<path> caminho para o prompt, e.g. /bin/bash
	@$(COMPOSE_FILE_CMD) exec -it $(c) $(b)

ps: status ## Alias do comando 'status'

topology: ## Gera um diagrama dos serviços listados no arquivo YML do Docker Compose
	@./scripts/sh/generate-diagrams.sh topology

runws: ## Realiza o empacotamento e execução do JAR, iniciando o web service localmente
	@./scripts/sh/run-ws.sh service

tests: ## Executa os testes unitários localmente
	@./scripts/sh/run-ws.sh tests
