FROM mysql:9

ARG MYSQL_DATABASE
ENV MYSQL_DATABASE=${MYSQL_DATABASE}

# DB initialization SQL script
COPY ./infra/docker/database/init-db.sql /etc/mysql/init-db.sql

RUN chown -R mysql:root /var/lib/mysql/ \
    && sed -i 's/MYSQL_DATABASE/'${MYSQL_DATABASE}'/g' /etc/mysql/init-db.sql \
    && cp /etc/mysql/init-db.sql /docker-entrypoint-initdb.d

EXPOSE 3306
