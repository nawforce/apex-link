package io.github.nahforce.apexlink

import java.io.StringReader

import io.github.nahforce.apexlink.antlr.{ApexLexer, ApexParser}
import io.github.nahforce.apexlink.cst._
import io.github.nahforce.apexlink.utils.{CSTException, CaseInsensitiveInputStream, ThrowingErrorListener}
import junit.framework.TestCase
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

    val listener = new ThrowingErrorListener
    val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(new StringReader(p))

    val lexer: ApexLexer = new ApexLexer(cis)
    lexer.removeErrorListeners()
    lexer.addErrorListener(listener)

    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    val parser: ApexParser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)

    Primary.construct(parser.primary()).getType(typeCtx)
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
}

class TypeTestPrimary extends TestCase {

  def primary(p: String, r: Type, ctx: TypeContext = null) =
    TypeTestHelper.comparePrimary(p, r, ctx)

  def testPrimaryLiteral(): Unit = {
    primary("0", IntegerType(0))
    primary("1", IntegerType(0))
    primary("0l", LongType(0))
    primary("1l", LongType(0))
    primary("0L", LongType(0))
    primary("1L", LongType(0))
    primary("''", StringType(0))
    primary("'a'", StringType(0))
    primary("'az'", StringType(0))
    primary("'\t'", StringType(0))
    primary("true", BooleanType(0))
    primary("False", BooleanType(0))
    primary("null", NullType())
    primary("0.0", DecimalType(0))
    primary(".0", DecimalType(0))
    primary("0.123", DecimalType(0))
    primary("0.123456789012345678901234567890123456789012345678", DecimalType(0))
    primary("0.1234567890123456789012345678901234567890123456789", DoubleType(0))
  }

  def testClassLiteral(): Unit = {
    primary("void.class", VoidClassType())
    primary("Integer.class", ClassType(PrimitiveTypeRef(IntegerType(0))))
    primary("Integer[].class", ClassType(PrimitiveTypeRef(IntegerType(1))))
    primary("String[][].class", ClassType(PrimitiveTypeRef(StringType(2))))

    // TODO: Can these be simplified
    primary("foo.class", ClassType(ClassOrInterfaceTypeRef(ClassOrInterfaceType(List(ClassOrInterfaceTypePart("foo", TypeList(List())))), 0)))
    primary("foo<bar>.class", ClassType(ClassOrInterfaceTypeRef(ClassOrInterfaceType(List(ClassOrInterfaceTypePart("foo", TypeList(List(ClassOrInterfaceType(List(ClassOrInterfaceTypePart("bar", TypeList(List()))))))))), 0)))
    primary("foo<bar>[].class", ClassType(ClassOrInterfaceTypeRef(ClassOrInterfaceType(List(ClassOrInterfaceTypePart("foo", TypeList(List(ClassOrInterfaceType(List(ClassOrInterfaceTypePart("bar", TypeList(List()))))))))), 1)))

    // TODO: Is this complete?
  }

  def testThisLiteral(): Unit = {
    val ctx = new TypeContextTest(_thisType = NullType())
    primary("this", NullType(), ctx)
  }

  def testSuperLiteral(): Unit = {
    val ctx = new TypeContextTest(_superType = NullType())
    primary("super", NullType(), ctx)
  }

  def testField(): Unit = {
    val ctx = new TypeContextTest(identifierTypes = Map(("anId", NullType())))
    primary("anId", NullType(), ctx)
    // TODO: Identifier handling in lexer
  }
}

object TestMain {
  def main(args: Array[String]): Unit = {
    val tt = new TypeTestPrimary()
    tt.testPrimaryLiteral()
    tt.testClassLiteral()
    tt.testThisLiteral()
    tt.testSuperLiteral()
    tt.testField()
  }
}
