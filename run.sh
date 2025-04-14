#!/bin/sh
./gradlew build -p tool && \
java -cp tool/build/libs/tool.jar com.craftinginterpreters.tool.GenerateAst lox/src/main/java/com/craftinginterpreters/lox && \
./gradlew build -p lox && \
java -cp lox/build/libs/lox.jar com.craftinginterpreters.lox.Lox $1
