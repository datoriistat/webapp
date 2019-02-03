#!/usr/bin/env bash

set -eux

NAME="datoriistat"

mvn clean package

docker build -t ${NAME} .
docker stop ${NAME} && docker rm ${NAME}
docker run -d --restart=always -p 6841:8080 --name ${NAME} ${NAME}
