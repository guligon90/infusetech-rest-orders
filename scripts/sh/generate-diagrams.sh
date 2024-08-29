#!/bin/bash

function topology() {
    OUTPUT_FILE="project-docker-topology.png"
    DOCKER_COMPOSE_FILE="docker-compose.yml"
    DOCKER_COMPOSE_DIAGRAM_LOCATION="docs/img"

    chmod 777 "./$DOCKER_COMPOSE_DIAGRAM_LOCATION"

    docker run \
		--rm \
		-it \
		--name dcv \
        -v "$(pwd):/input:rw" \
        -v "$(pwd)/$DOCKER_COMPOSE_DIAGRAM_LOCATION:/output:rw" \
		pmsipilot/docker-compose-viz \
		render \
			-m \
			image \
			--force \
            --horizontal \
			--output-file /output/$OUTPUT_FILE \
            $DOCKER_COMPOSE_FILE
}

$@
