# +---------------------+
# | JAR packaging stage |
# +---------------------+
FROM maven:3-openjdk-17-slim as build

ENV WORKDIR=/home/webservice 
WORKDIR ${WORKDIR}

COPY ./src ${WORKDIR}/src
COPY ./pom.xml ${WORKDIR}/pom.xml

RUN mvn clean install \
    -Dmaven.test.skip \
    -DskipTests

# +---------------------+
# | JAR execution stage |
# +---------------------+
FROM openjdk:17-jdk-alpine

ENV APP_NAME=orders 
ENV VERSION=0.0.1-SNAPSHOT
ENV ARTIFACT=${APP_NAME}-${VERSION}.jar

ENV WORKDIR=/home/webservice 
WORKDIR ${WORKDIR}

COPY --from=build ${WORKDIR}/target/${ARTIFACT} ./${ARTIFACT}

EXPOSE 8080

ENTRYPOINT java -jar ./${ARTIFACT}
