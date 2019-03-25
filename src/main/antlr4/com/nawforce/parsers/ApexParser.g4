/*
 [The "BSD licence"]
 Copyright (c) 2013 Terence Parr, Sam Harwell
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/** 
 *  An Apexcode grammar derived from Java 1.7 grammar for ANTLR v4.
 *  Uses ANTLR v4's left-recursive expression notation.
 *  
 *  @maintainer: Andrey Gavrikov
 *
 *  You can test with
 *
 *  $ antlr4 ApexGrammar.g4
 *  $ javac *.java
 *  $ grun Apexcode compilationUnit *.cls
 */
parser grammar ApexParser;
options {tokenVocab=ApexLexer;}

@header {
import java.util.*; 
}

// starting point for parsing a apexcode file
compilationUnit
    :   typeDeclaration EOF
    ;

typeDeclaration
    :   modifier* classDeclaration
    |   modifier* enumDeclaration
    |   modifier* interfaceDeclaration
    ;

classDeclaration
    :   CLASS id
        (EXTENDS typeRef)?
        (IMPLEMENTS typeList)?
        classBody
    ;

enumDeclaration
    :   ENUM id (IMPLEMENTS typeList)?
        LBRACE enumConstants? COMMA ? enumBodyDeclarations? RBRACE
    ;

enumConstants
    :   enumConstant (COMMA enumConstant)*
    ;

enumConstant
    :   annotation* id arguments? classBody?
    ;

enumBodyDeclarations
    :   SEMI classBodyDeclaration*
    ;

interfaceDeclaration
    :   INTERFACE id (EXTENDS typeList)? interfaceBody
    ;

typeList
    :   typeRef (COMMA typeRef)*
    ;

classBody
    :   LBRACE classBodyDeclaration* RBRACE
    ;

interfaceBody
    :   LBRACE interfaceBodyDeclaration* RBRACE
    ;

classBodyDeclaration
    :   SEMI
    |   STATIC? block
    |   modifier* memberDeclaration
    ;

/* Unify all modifiers so we can give better error messages */
modifier
    :   annotation
    |   GLOBAL
    |   PUBLIC
    |   PROTECTED
    |   PRIVATE
    |   TRANSIENT
    |   STATIC
    |   ABSTRACT
    |   FINAL
    |   WEBSERVICE
    |   OVERRIDE
    |   VIRTUAL
    |   TESTMETHOD
    |	WITH SHARING
    |	WITHOUT SHARING
    |	INHERITED SHARING
    ;

memberDeclaration
    :   methodDeclaration
    |   fieldDeclaration
    |   constructorDeclaration
    |   interfaceDeclaration
    |   classDeclaration
    |   enumDeclaration
    |   propertyDeclaration
    ;

/* We use rule this even for void methods which cannot have [] after parameters.
   This simplifies grammar and we can consider void to be a type, which
   renders the [] matching as a context-sensitive issue or a semantic check
   for invalid return type after parsing.
 */


methodDeclaration
    :   annotation? modifier* (typeRef|VOID) id formalParameters
        (   block
        |   SEMI
        )
    ;

constructorDeclaration
    :   qualifiedName formalParameters block
    ;

fieldDeclaration
    :   typeRef variableDeclarators SEMI
    ;

propertyDeclaration
    :   typeRef variableDeclarators propertyBodyDeclaration
    ;

propertyBodyDeclaration
    :   LBRACE propertyBlock propertyBlock? RBRACE
    ;

interfaceBodyDeclaration
    :   modifier* interfaceMemberDeclaration
    |   SEMI
    ;

interfaceMemberDeclaration
    :   constDeclaration
    |   interfaceMethodDeclaration
    |   interfaceDeclaration
    |   classDeclaration
    |   enumDeclaration
    ;

constDeclaration
    :   typeRef constantDeclarator (COMMA constantDeclarator)* SEMI
    ;

constantDeclarator
    :   id (LBRACK RBRACK)* '=' variableInitializer
    ;

interfaceMethodDeclaration
    :   (typeRef|VOID) id formalParameters SEMI
    ;

variableDeclarators
    :   variableDeclarator (COMMA variableDeclarator)*
    ;

variableDeclarator
    :   id (ASSIGN variableInitializer)?
    ;

variableInitializer
    :   arrayInitializer
    |   expression
    ;

arrayInitializer
    :   LBRACE (variableInitializer (COMMA variableInitializer)* (COMMA)? )? RBRACE
    ;

typeRef
    :   classOrInterfaceType arraySubscripts
    |   primitiveType arraySubscripts
    ;

arraySubscripts
    :   (LBRACK RBRACK)*
    ;

classOrInterfaceType
    :   id typeArguments? (DOT id typeArguments? )*
    ;

primitiveType
    :   BLOB
    |   BOOLEAN
    |   DATE
    |   DATETIME
    |   DECIMAL
    |   DOUBLE
    |   ID
    |   INTEGER
    |   LONG
    |   OBJECT
    |   STRING
    |   TIME
    ;

typeArguments
    :   LT typeList GT
    ;

formalParameters
    :   LPAREN formalParameterList? RPAREN
    ;

formalParameterList
    :   formalParameter (COMMA formalParameter)*
    ;

formalParameter
    :   modifier* typeRef id
    ;

qualifiedName
    :   id (DOT id)*
    ;

literal
    :   IntegerLiteral
    |   NumberLiteral
    |   StringLiteral
    |   BooleanLiteral
    |   NULL
    ;

// ANNOTATIONS

annotation
    :   AT qualifiedName ( LPAREN ( elementValuePairs | elementValue )? RPAREN )?
    ;

elementValuePairs
    :   elementValuePair (COMMA? elementValuePair)*
    ;

elementValuePair
    :   id ASSIGN elementValue
    ;

elementValue
    :   expression
    |   annotation
    |   elementValueArrayInitializer
    ;

elementValueArrayInitializer
    :   LBRACE (elementValue (COMMA elementValue)*)? (COMMA)? RBRACE
    ;

// STATEMENTS / BLOCKS

block
    :   LBRACE statement* RBRACE
    ;

localVariableDeclarationStatement
    :    localVariableDeclaration SEMI
    ;

localVariableDeclaration
    :   modifier* typeRef variableDeclarators
    ;

statement
    :   block
    |   localVariableDeclarationStatement                           // TODO: Is this right or dodgy code
    |   ifStatement
    |   forStatement                                               
    |   whileStatement
    |   doWhileStatement
    |   tryStatement
    |   returnStatement
    |   throwStatement
    |   breakStatement
    |   continueStatement
    |   insertStatement
    |   updateStatement
    |   deleteStatement
    |   undeleteStatement
    |   upsertStatement
    |   mergeStatement
    |   runAsStatement
    |   emptyStatement
    |   expressionStatement
    |   idStatement
    ;

ifStatement
    : IF parExpression statement (ELSE statement)?
    ;

forStatement
    : FOR LPAREN forControl RPAREN statement
    ;

whileStatement
    : WHILE parExpression statement
    ;

doWhileStatement
    : DO statement WHILE parExpression SEMI
    ;

tryStatement
    : TRY block (catchClause+ finallyBlock? | finallyBlock)
    ;

returnStatement
    : RETURN expression? SEMI
    ;

throwStatement
    : THROW expression SEMI
    ;

breakStatement
    : BREAK id? SEMI
    ;

continueStatement
    : CONTINUE id? SEMI
    ;

insertStatement
    : INSERT expression SEMI
    ;

updateStatement
    : UPDATE expression SEMI
    ;

deleteStatement
    : DELETE expression SEMI
    ;

undeleteStatement
    : UNDELETE expression SEMI
    ;

upsertStatement
    : UPSERT expression id? SEMI
    ;

mergeStatement
    : MERGE expression expression SEMI
    ;

runAsStatement
    : RUNAS LPAREN expressionList? RPAREN block?
    ;

emptyStatement
    : SEMI
    ;

expressionStatement
    :   expression SEMI
    ;

idStatement
    :   id COLON statement
    ;

propertyBlock
	:	modifier* (getter | setter)
	;

getter
    : GET (SEMI | block)
    ;

setter
    : SET (SEMI | block)
    ;

catchClause
    : CATCH LPAREN modifier* catchType id RPAREN block
    ;

catchType
    : qualifiedName (BITOR qualifiedName)*
    ;

finallyBlock
    : FINALLY block
    ;

forControl
    :  enhancedForControl
    |  forInit? SEMI expression? SEMI forUpdate?
    ;

forInit
    :  localVariableDeclaration
    |  expressionList
    ;

enhancedForControl
    :  modifier* typeRef id COLON expression
    ;

forUpdate
    :  expressionList
    ;

// EXPRESSIONS

parExpression
    :  LPAREN expression RPAREN
    ;

expressionList
    :  expression (COMMA expression)*
    ;

expression
    :   expression DOT id                                                                               # alt1Expression
    |   expression DOT THIS                                                                             # alt2Expression
    |   expression DOT NEW nonWildcardTypeArguments? innerCreator                                       # alt3Expression
    |   expression DOT SUPER superSuffix                                                                # alt4Expression
    |   expression DOT explicitGenericInvocation                                                        # alt5Expression
    |   expression LBRACK expression RBRACK                                                             # alt6Expression
    |   expression LPAREN expressionList? RPAREN                                                        # functionCallExpression
    |   NEW creator                                                                                     # alt8Expression
    |   LPAREN typeRef RPAREN expression                                                                # alt9Expression
    |   expression (INC | DEC)                                                                          # alt10Expression
    |   (ADD|SUB|INC|DEC) expression                                                                    # alt11Expression
    |   (TILDE|BANG) expression                                                                         # alt12Expression
    |   expression (MUL|DIV|MOD) expression                                                             # alt13Expression
    |   expression (ADD|SUB) expression                                                                 # alt14Expression
    |   expression (LT LT | GT GT GT | GT GT) expression                                                # alt15Expression
    |   expression (LT ASSIGN | GT ASSIGN | LE | GE | GT | LT) expression                               # alt16Expression
    |   expression INSTANCEOF typeRef                                                                   # alt17Expression
    |   expression (TRIPLEEQUAL | TRIPLENOTEQUAL | EQUAL | NOTEQUAL | LESSANDGREATER ) expression       # alt18Expression
    |   expression BITAND expression                                                                    # alt19Expression
    |   expression CARET expression                                                                     # alt20Expression
    |   expression BITOR expression                                                                     # alt21Expression
    |   expression AND expression                                                                       # alt22Expression
    |   expression OR expression                                                                        # alt23Expression
    |   expression QUESTION expression COLON expression                                                 # alt24Expression
    |   <assoc=right> expression
        (   ASSIGN
        |   ADD_ASSIGN
        |   SUB_ASSIGN
        |   MUL_ASSIGN
        |   DIV_ASSIGN
        |   AND_ASSIGN
        |   OR_ASSIGN
        |   XOR_ASSIGN
        |   RSHIFT_ASSIGN
        |   URSHIFT_ASSIGN
        |   LSHIFT_ASSIGN
        |   MOD_ASSIGN
        )
        expression                                                                                     # alt25Expression
    |   primary                                                                                        # alt26Expression
    ;

primary
    :   LPAREN expression RPAREN                                                                       # alt1Primary
    |   THIS                                                                                           # alt2Primary
    |   SUPER                                                                                          # alt3Primary
    |   literal                                                                                        # alt4Primary
    |   id                                                                                             # alt5Primary
    |   typeRef DOT CLASS                                                                              # alt6Primary
    |   VOID DOT CLASS                                                                                 # alt7Primary
    |   nonWildcardTypeArguments (explicitGenericInvocationSuffix | THIS arguments)                    # alt8Primary
    |   soqlLiteral                                                                                    # alt9Primary
    ;

creator
    :   nonWildcardTypeArguments createdName classCreatorRest                                          #alt1Creator
    |   createdName (arrayCreatorRest | classCreatorRest | mapCreatorRest | setCreatorRest)            #alt2Creator
    ;

createdName
    :   idCreatedNamePair (DOT idCreatedNamePair)*
    |   primitiveType
    ;

idCreatedNamePair
    :   id typeArgumentsOrDiamond?
    ;

innerCreator
    :   id nonWildcardTypeArgumentsOrDiamond? classCreatorRest
    ;

arrayCreatorRest
    :   LBRACK
        (   RBRACK arraySubscripts arrayInitializer
        |   expression RBRACK (LBRACK expression RBRACK)* arraySubscripts
        )
    ;

mapCreatorRest
    :   LBRACE mapCreatorRestPair (COMMA mapCreatorRestPair )* RBRACE
    ;

mapCreatorRestPair
    :   idOrExpression MAP literalOrExpression
    ;

setCreatorRest
	: LBRACE literalOrExpression (COMMA ( literalOrExpression ))* RBRACE
	;

literalOrExpression
    :   literal | expression
    ;

idOrExpression
    :   id | expression
    ;

classCreatorRest
    :   arguments
    |   LBRACE expressionList? RBRACE
    ;

explicitGenericInvocation
    :   nonWildcardTypeArguments explicitGenericInvocationSuffix
    ;

nonWildcardTypeArguments
    :   LT typeList GT
    ;

typeArgumentsOrDiamond
    :   LT GT
    |   typeArguments
    ;

nonWildcardTypeArgumentsOrDiamond
    :   LT GT
    |   nonWildcardTypeArguments
    ;

superSuffix
    :   arguments
    |   DOT id arguments?
    ;

explicitGenericInvocationSuffix
    :   SUPER superSuffix
    |   id arguments
    ;

arguments
    :   LPAREN expressionList? RPAREN
    ;


soqlLiteral
    : LBRACK (soqlLiteral|~RBRACK)*? RBRACK
    ;

id
    :  Identifier
    |  'get'
    |  'set'
    |  'blob'
    |  'boolean'
    |  'date'
    |  'datetime'
    |  'decimal'
    |  'double'
    |  'id'
    |  'integer'
    |  'long'
    |  'object'
    |  'string'
    |  'time'
    |  'select'
    |  'insert'
    |  'upsert'
    |  'update'
    |  'delete'
    |  'undelete'
    |  'merge'
    |  'new'
    |  'for'
    ;

