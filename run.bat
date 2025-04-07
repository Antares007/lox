javac com/craftinginterpreters/tool/*.java && ^
java  com.craftinginterpreters.tool.GenerateAst com\craftinginterpreters\lox && ^
javac com/craftinginterpreters/lox/*.java && ^
java  com.craftinginterpreters.lox.%1 %2

