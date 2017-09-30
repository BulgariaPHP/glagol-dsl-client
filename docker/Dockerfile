FROM openjdk:8-jdk-alpine

MAINTAINER Yoan-Alexander Grigorov <joan.grigorov@gmail.com>

RUN mkdir /usr/local/src

ADD glagol-client /usr/bin/glagol-client
ADD glagol-dsl-client.jar /usr/local/src/glagol-dsl-client.jar

RUN chmod +x /usr/bin/glagol-client \
    && mkdir /app

WORKDIR /app

ENTRYPOINT ["glagol-client"]