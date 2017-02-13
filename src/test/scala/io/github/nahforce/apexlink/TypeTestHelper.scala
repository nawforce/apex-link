package io.github.nahforce.apexlink

import java.io.StringReader

import io.github.nahforce.apexlink.antlr.{ApexLexer, ApexParser}
import io.github.nahforce.apexlink.cst.{Expression, Primary, Type, TypeContext}
import io.github.nahforce.apexlink.utils.{CSTException, CaseInsensitiveInputStream, ThrowingErrorListener}
import org.antlr.v4.runtime.CommonTokenStream

class TypeContextTest(_thisType: Type = null, _superType: Type = null, identifierTypes: Map[String, Type] = null) extends TypeContext {
  def thisType: Type =
    if (_thisType == null)
      throw new CSTException()
    else
      _thisType

  def superType: Type =
    if (_superType == null)
      throw new CSTException()
    else
      _superType

  def getIdentifierType(id: String): Type =
    if (identifierTypes == null || identifierTypes.get(id).isEmpty)
      throw new CSTException()
    else
      identifierTypes(id)
}

object TypeTestHelper {

  def typePrimary(p: String, typeCtx: TypeContext): Type = {
    Primary.construct(parse(p).primary()).getType(typeCtx)
  }

  def typeExpression(p: String, typeCtx: TypeContext): Expression = {
    Expression.construct(parse(p).expression())
  }

  def comparePrimary(p: String, r: Type, typeCtx: TypeContext): Unit = {
    val t = typePrimary(p, typeCtx)
    if (t == null)
      throw new CSTException

    if (t != r) {
      System.out.println("Type mismatch:")
      System.out.println("Expected: " + r)
      System.out.println("Got: " + t)
      assert(false)
    }
  }

  private def parse(p: String) = {
    val listener = new ThrowingErrorListener
    val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(new StringReader(p))

    val lexer: ApexLexer = new ApexLexer(cis)
    lexer.removeErrorListeners()
    lexer.addErrorListener(listener)

    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    val parser: ApexParser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)
    parser
  }
}
