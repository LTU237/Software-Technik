FROM mysql:latest

ENV MYSQL_DATABASE=10_tables
ENV MYSQL_ALLOW_EMPTY_PASSWORD=true

COPY ./conf/* /etc/mysql/conf.d
COPY ./docker-entrypoint-initdb/* /docker-entrypoint-initdb.d/