#!/bin/bash

./gradlew build

docker build -t url-shortener-service .

docker-compose up -d