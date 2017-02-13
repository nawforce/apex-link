package io.github.nahforce.apexlink

import io.github.nahforce.apexlink.cst._
import org.junit.Test

class TypePrimaryTest
{
  def primary(p: String, r: Type, ctx: TypeContext = null) : Unit =
    TypeTestHelper.comparePrimary(p, r, ctx)

  @Test def testPrimaryLiteral(): Unit = {
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

  @Test def testThisLiteral(): Unit = {
    val ctx = new TypeContextTest(_thisType = NullType())
    primary("this", NullType(), ctx)
  }

  @Test def testSuperLiteral(): Unit = {
    val ctx = new TypeContextTest(_superType = NullType())
    primary("super", NullType(), ctx)
  }

  @Test def testField(): Unit = {
    val ctx = new TypeContextTest(identifierTypes = Map(("anId", NullType())))
    primary("anId", NullType(), ctx)
    // TODO: Identifier handling in lexer
  }
}

object TestMain {
  def main(args: Array[String]): Unit = {
    val tt = new TypePrimaryTest()
    tt.testPrimaryLiteral()
    //tt.testClassLiteral()
    tt.testThisLiteral()
    tt.testSuperLiteral()
    tt.testField()
  }
}
