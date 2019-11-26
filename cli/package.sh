#!/bin/bash

rm -rf jars
mkdir jars
cp ../target/apexlink-0.5.jar jars/.
cp ../target/dependency/antlr4-runtime-*.jar jars/.
cp ../target/dependency/logback-classic-*.jar jars/.
cp ../target/dependency/logback-core-*.jar jars/.
cp ../target/dependency/scala-library-*.jar jars/.
cp ../target/dependency/scala-logging_*.jar jars/.
cp ../target/dependency/scala-reflect-*.jar jars/.
cp ../target/dependency/scala-xml_*.jar jars/.
cp ../target/dependency/scalaz-core_*.jar jars/.
cp ../target/dependency/slf4j-api-*.jar jars/.
cp ../target/dependency/lift-json_*.jar jars/.
