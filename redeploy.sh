#!/usr/bin/env bash

export LAUNCHER="io.vertx.core.Launcher"
export VERTICLE="io.vertx.starter.MainVerticle"
export CMD="mvn compile"
export VERTX_CMD="run"
export JAVA_OPT="-Duser.language=en -Duser.country=US -Duser.variant=en_US -Dhazelcast.logging.type=slf4j"

mvn compile dependency:copy-dependencies
java \
  -cp  $(echo target/dependency/*.jar | tr ' ' ':'):"target/classes" \
  $LAUNCHER $VERTX_CMD $VERTICLE \
  --java-opts=$JAVA_OPT \
  --redeploy="src/main/**/*.java" --on-redeploy="$CMD" \
  --launcher-class=$LAUNCHER \
  $@
