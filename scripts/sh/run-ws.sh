#!/bin/bash

function locally() {
    clear;

    echo "Repackaging JAR..."
    mvn clean package -Dmaven.test.skip

    echo "Executing web service..."
    java -jar ./target/orders-0.0.1-SNAPSHOT.jar
}

$@
