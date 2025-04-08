package com.craftinginterpreters.lox;
class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }
  @Override
  public String visitCallExpr(Expr.Call expr) {
    StringBuilder b = new StringBuilder();
    b.append("(");
    b.append(expr.callee.accept(this));
    for (Expr argument : expr.arguments) {
      b.append(", ");
      b.append(argument.accept(this));
    }
    b.append(")");
    return b.toString();
  }
  @Override
  public String visitWhileStmt(Stmt.While stmt) {
    StringBuilder builder = new StringBuilder();
    builder.append("(while, ");
    builder.append(stmt.condition.accept(this));
    builder.append(", ");
    builder.append(stmt.body.accept(this));
    builder.append(")");
    return builder.toString();
  }
  @Override
  public String visitLogicalExpr(Expr.Logical expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }
  @Override
  public String visitIfStmt(Stmt.If stmt) {
    StringBuilder builder = new StringBuilder();
    builder.append("(if, ");
    builder.append(stmt.condition.accept(this));
    builder.append(", ");
    builder.append(stmt.thenBranch.accept(this));
    if (stmt.elseBranch != null) {
      builder.append(", ");
      builder.append(stmt.elseBranch.accept(this));
    }
    builder.append(")");
    return builder.toString();
  }
  @Override
  public String visitBlockStmt(Stmt.Block block) {
    StringBuilder builder = new StringBuilder();
    builder.append("{\n");
    for (Stmt statement : block.statements) {
      builder.append(statement.accept(this));
      builder.append("\n");
    }
    builder.append("}");
    return "";
  }

  @Override
  public String visitAssignExpr(Expr.Assign expr) {
    return parenthesize(expr.name.literal.toString(), expr.value);
  }
  @Override
  public String visitVariableExpr(Expr.Variable expr) {
    return expr.name.literal.toString();
  }

  @Override
  public String visitVarStmt(Stmt.Var stmt) {
    return parenthesize(stmt.name.literal.toString(), stmt.initializer);
  }

  @Override
  public String visitPrintStmt(Stmt.Print stmt) {
    return parenthesize("print ", stmt.expression);
  }
  @Override
  public String visitExpressionStmt(Stmt.Expression expression) {
    return parenthesize("ExpressionStmt ", expression.expression);
  }
  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }
  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize("group", expr.expression);
  }
  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null) return "nil";
    return expr.value.toString();
  }
  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }
  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();
    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }
    builder.append(")");
    return builder.toString();
  }
  public static void main(String[] args) {
    Expr expression =
      new Expr.Binary(
          new Expr.Unary(
            new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(123)
            ),
          new Token(TokenType.STAR, "*", null, 1), new Expr.Grouping( new Expr.Literal(45.67)));
    System.out.println(new AstPrinter().print(expression));
  }
}
