# +---------------------+
# | JAR packaging stage |
# +---------------------+
FROM maven:3-openjdk-17-slim as build

ENV WORKDIR=/home/app 
WORKDIR ${WORKDIR}

COPY ./src ${WORKDIR}/src
COPY ./pom.xml ${WORKDIR}/pom.xml

RUN mvn clean package -Dmaven.test.skip

# +---------------------+
# | JAR execution stage |
# +---------------------+
FROM openjdk:17-jdk-alpine

ENV APP_NAME=orders 
ENV VERSION=0.0.1-SNAPSHOT
ENV ARTIFACT=${APP_NAME}-${VERSION}.jar

ENV WORKDIR=/home/app 
WORKDIR ${WORKDIR}

COPY --from=build ${WORKDIR}/target/${ARTIFACT} ./${ARTIFACT}

EXPOSE 8080

ENTRYPOINT java -jar ./${ARTIFACT}

# FROM maven:3-openjdk-17-slim as build

# ENV WORKDIR=/home/app 
# ENV APP_NAME=orders 
# ENV VERSION=0.0.1-SNAPSHOT
# ENV ARTIFACT=${APP_NAME}-${VERSION}.jar

# WORKDIR ${WORKDIR}

# COPY ./src ${WORKDIR}/src
# COPY ./pom.xml ${WORKDIR}/pom.xml

# RUN mvn clean package -Dmaven.test.skip

# EXPOSE 8080

# # CMD [ "/bin/bash" ]
# ENTRYPOINT java -jar ./target/${ARTIFACT}
