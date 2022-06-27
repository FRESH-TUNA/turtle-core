#!/usr/bin/env sh

export $(grep -v '^#' ../.env | xargs)

java -Dspring.profiles.active=prod -jar ../build/libs/core-0.0.1-SNAPSHOT.jar
