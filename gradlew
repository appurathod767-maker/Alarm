#!/usr/bin/env bash
set -e
if [ -x "./gradlew" ]; then
  exec ./gradlew "$@"
else
  echo "Please run with your local gradle or install the gradle wrapper."
  exit 1
fi
