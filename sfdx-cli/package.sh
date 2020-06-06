#!/bin/bash

(cd ..; mvn dependency:copy-dependencies -DincludeScope=runtime)
rm -rf jars
mkdir jars
cp ../target/apexlink*.jar jars/.
rm jars/apexlink*-javadoc.jar
rm jars/apexlink*-sources.jar
cp ../target/dependency/* jars/.
