#!/usr/bin/env bash

set -eux

mvn clean package

docker build -t datoriistat .
docker stop datoriistat
docker run -d --restart=always -p 6841:8080 --name datoriistat datoriistat
