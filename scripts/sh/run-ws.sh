#!/bin/bash

function service() {
    clear;

    echo "Repackaging JAR..."
    mvn clean package -Dmaven.test.skip

    echo "Running web service..."
    java -jar ./target/orders-0.0.1-SNAPSHOT.jar
}

function tests() {
    clear;

    echo "Running JUnit tests..."
    mvn clean test
}

$@
