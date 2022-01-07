FROM liquibase/liquibase:latest

USER root

RUN apt-get update \
    && apt-get -y install sudo git

COPY entry.sh /entry.sh

ENTRYPOINT ["/entry.sh"]