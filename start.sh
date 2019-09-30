#!/bin/bash
mvn spring-boot:run -Drun.jvmArguments="-Xmx128m" & echo $! > ./pid.file &
