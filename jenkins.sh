#!/usr/bin/env bash

set -eux

docker build -t datoriistat .
docker run -d --restart=always datoriistat
