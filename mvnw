#!/bin/sh
set -e
DIR="$(cd "$(dirname "$0")" && pwd)"
MVNW_REPOURL="https://repo.maven.apache.org/maven2"
exec java -jar "$DIR/.mvn/wrapper/maven-wrapper.jar" "$@"
