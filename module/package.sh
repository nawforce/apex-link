#!/bin/bash

rm -rf jars
mkdir jars
export APEXLINK_TARGET=../runtime/target
cp $APEXLINK_TARGET/apexlink-0.5.jar jars/.
cp $APEXLINK_TARGET/dependency/antlr4-runtime-*.jar jars/.
cp $APEXLINK_TARGET/dependency/logback-classic-*.jar jars/.
cp $APEXLINK_TARGET/dependency/logback-core-*.jar jars/.
cp $APEXLINK_TARGET/dependency/scala-library-*.jar jars/.
cp $APEXLINK_TARGET/dependency/scala-logging_*.jar jars/.
cp $APEXLINK_TARGET/dependency/scala-reflect-*.jar jars/.
cp $APEXLINK_TARGET/dependency/scala-xml_*.jar jars/.
cp $APEXLINK_TARGET/dependency/scalaz-core_*.jar jars/.
cp $APEXLINK_TARGET/dependency/slf4j-api-*.jar jars/.
cp $APEXLINK_TARGET/dependency/lift-json_*.jar jars/.
