#!/bin/sh

# Dry run
mvn release:clean
mvn -B release:prepare -DdryRun=true

# Perform
mvn clean
mvn release:clean
mvn -B release:prepare -Dresume=false
mvn release:perform



