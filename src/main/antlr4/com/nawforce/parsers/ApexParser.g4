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
    :   typeDeclaration* EOF
    ;

typeDeclaration
    :   classOrInterfaceModifier* classDeclaration
    |   classOrInterfaceModifier* enumDeclaration
    |   classOrInterfaceModifier* interfaceDeclaration
    |   ';'
    ;

modifier
    :   classOrInterfaceModifier
    |   (   'native'
        |   'synchronized'
        |   'transient'
        )
    ;

classOrInterfaceModifier
    :   annotation       // class or interface
    |   (   'public'     // class or interface
        |   'protected'  // class or interface
        |   'private'    // class or interface
        |   'static'     // class or interface
        |   'abstract'   // class or interface
        |   'final'      // class only -- does not apply to interfaces
        |   'global'     // class or interface
        |   'webservice' // class only -- does not apply to interfaces
        |   'override'   // method only
        |   'virtual'    // method only
        |   'testmethod'   // method only
		|	withSharing  // class only
		|	withoutSharing //class only
        )
    ;

variableModifier
    :   'final'
    |   'transient'
    |   annotation
    ;

classDeclaration
    :   'class' id
        ('extends' typeRef)?
        ('implements' typeList)?
        classBody
    ;


enumDeclaration
    :   ENUM id ('implements' typeList)?
        '{' enumConstants? ','? enumBodyDeclarations? '}'
    ;

enumConstants
    :   enumConstant (',' enumConstant)*
    ;

enumConstant
    :   annotation* id arguments? classBody?
    ;

enumBodyDeclarations
    :   ';' classBodyDeclaration*
    ;

interfaceDeclaration
    :   'interface' id ('extends' typeList)? interfaceBody
    ;

typeList
    :   typeRef (',' typeRef)*
    ;

classBody
    :   '{' classBodyDeclaration* '}'
    ;

interfaceBody
    :   '{' interfaceBodyDeclaration* '}'
    ;

classBodyDeclaration
    :   ';'
    |   'static'? block
    |   modifier* memberDeclaration
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

methodModifier
    :   'global'
    |   'public'
    |   'protected'
    |   'private'
    |   'static' 
    |   'webservice'
    |   'override'
    |   'virtual'
    |   'testmethod'
    ;

methodDeclaration
    :   annotation? methodModifier* (typeRef|'void') id formalParameters
        (   block
        |   ';'
        )
    ;

constructorDeclaration
    :   qualifiedName formalParameters block
    ;

fieldDeclaration
    :   typeRef variableDeclarators ';'
    ;

propertyDeclaration
    :   typeRef variableDeclarators propertyBodyDeclaration
    ;

propertyBodyDeclaration
    :   '{' propertyBlock propertyBlock? '}'
    ;

interfaceBodyDeclaration
    :   modifier* interfaceMemberDeclaration
    |   ';'
    ;

interfaceMemberDeclaration
    :   constDeclaration
    |   interfaceMethodDeclaration
    |   interfaceDeclaration
    |   classDeclaration
    |   enumDeclaration
    ;

constDeclaration
    :   typeRef constantDeclarator (',' constantDeclarator)* ';'
    ;

constantDeclarator
    :   id ('[' ']')* '=' variableInitializer
    ;

interfaceMethodDeclaration
    :   (typeRef|'void') id formalParameters ';'
    ;

variableDeclarators
    :   variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    :   id ('=' variableInitializer)?
    ;

variableInitializer
    :   arrayInitializer
    |   expression
    ;

arrayInitializer
    :   '{' (variableInitializer (',' variableInitializer)* (',')? )? '}'
    ;

typeRef
    :   classOrInterfaceType arraySubscripts
    |   primitiveType arraySubscripts
    ;

arraySubscripts
    :   ('[' ']')*
    ;

classOrInterfaceType
    :   id typeArguments? ('.' id typeArguments? )*
    ;

primitiveType
    :   'blob'
    |   'boolean'
    |   'date'
    |   'datetime'
    |   'decimal'
    |   'double'
    |   'id'
    |   'integer'
    |   'long'
    |   'object'
    |   'string'
    |   'time'
    ;

typeArguments
    :   '<' typeList '>'
    ;

formalParameters
    :   '(' formalParameterList? ')'
    ;

formalParameterList
    :   formalParameter (',' formalParameter)*
    ;

formalParameter
    :   variableModifier* typeRef id
    ;

qualifiedName
    :   id ('.' id)*
    ;

literal
    :   IntegerLiteral
    |   NumberLiteral
    |   StringLiteral
    |   BooleanLiteral
    |   'null'
    ;

// ANNOTATIONS

annotation
    :   '@' qualifiedName ( '(' ( elementValuePairs | elementValue )? ')' )?
    ;

elementValuePairs
    :   elementValuePair (','? elementValuePair)*
    ;

elementValuePair
    :   id '=' elementValue
    ;

elementValue
    :   expression
    |   annotation
    |   elementValueArrayInitializer
    ;

elementValueArrayInitializer
    :   '{' (elementValue (',' elementValue)*)? (',')? '}'
    ;

// STATEMENTS / BLOCKS

block
    :   '{' statement* '}'
    ;

localVariableDeclarationStatement
    :    localVariableDeclaration ';'
    ;

localVariableDeclaration
    :   variableModifier* typeRef variableDeclarators
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
    |   bangStatement
    ;

ifStatement
    : 'if' parExpression statement ('else' statement)?
    ;

forStatement
    : 'for' '(' forControl ')' statement
    ;

whileStatement
    :   'while' parExpression statement
    ;

doWhileStatement
    :   'do' statement 'while' parExpression ';'
    ;

tryStatement
    :   'try' block (catchClause+ finallyBlock? | finallyBlock)
    ;

returnStatement
    :   'return' expression? ';'
    ;

throwStatement
    :   'throw' expression ';'
    ;

breakStatement
    :   'break' id? ';'
    ;

continueStatement
    :   'continue' id? ';'
    ;

insertStatement
    :   'insert' expression ';'
    ;

updateStatement
    :   'update' expression ';'
    ;

deleteStatement
    :   'delete' expression ';'
    ;

undeleteStatement
    :   'undelete' expression ';'
    ;

upsertStatement
    :   'upsert' expression id? ';'
    ;

mergeStatement
    :   'merge' expression expression ';'
    ;

runAsStatement
    :   RUNAS '(' expressionList? ')' block?
    ;

emptyStatement
    :   ';'
    ;

expressionStatement
    :   expression ';'
    ;

idStatement
    :   id ':' statement
    ;

bangStatement
    :   BANG_STATEMENT
    ;

propertyBlock
	:	modifier* (getter | setter)
	;

getter
 : 'get' (';' | block)
 ;

setter
 : 'set' (';' | block)
 ;

catchClause
    :   'catch' '(' variableModifier* catchType id ')' block
    ;

catchType
    :   qualifiedName ('|' qualifiedName)*
    ;

finallyBlock
    :   'finally' block
    ;

forControl
    :   enhancedForControl
    |   forInit? ';' expression? ';' forUpdate?
    ;

forInit
    :   localVariableDeclaration
    |   expressionList
    ;

enhancedForControl
    :   variableModifier* typeRef id ':' expression
    ;

forUpdate
    :   expressionList
    ;

// EXPRESSIONS

parExpression
    :   '(' expression ')'
    ;

expressionList
    :   expression (',' expression)*
    ;

expression
    :   expression '.' id                                                   # alt1Expression
    |   expression '.' 'this'                                               # alt2Expression
    |   expression '.' 'new' nonWildcardTypeArguments? innerCreator         # alt3Expression
    |   expression '.' 'super' superSuffix                                  # alt4Expression
    |   expression '.' explicitGenericInvocation                            # alt5Expression
    |   expression '[' expression ']'                                       # alt6Expression
    |   expression '(' expressionList? ')'                                  # functionCallExpression
    |   'new' creator                                                       # alt8Expression
    |   '(' typeRef ')' expression                                          # alt9Expression
    |   expression ('++' | '--')                                            # alt10Expression
    |   ('+'|'-'|'++'|'--') expression                                      # alt11Expression
    |   ('~'|'!') expression                                                # alt12Expression
    |   expression ('*'|'/'|'%') expression                                 # alt13Expression
    |   expression ('+'|'-') expression                                     # alt14Expression
    |   expression ('<' '<' | '>' '>' '>' | '>' '>') expression             # alt15Expression
    |   expression ('<' '=' | '>' '=' | '<=' | '>=' | '>' | '<') expression # alt16Expression
    |   expression 'instanceof' typeRef                                     # alt17Expression
    |   expression ('===' | '!==' | '==' | '!=' | '<>' ) expression         # alt18Expression
    |   expression '&' expression                                           # alt19Expression
    |   expression '^' expression                                           # alt20Expression
    |   expression '|' expression                                           # alt21Expression
    |   expression '&&' expression                                          # alt22Expression
    |   expression '||' expression                                          # alt23Expression
    |   expression '?' expression ':' expression                            # alt24Expression
    |   <assoc=right> expression
        (   '='
        |   '+='
        |   '-='
        |   '*='
        |   '/='
        |   '&='
        |   '|='
        |   '^='
        |   '>>='
        |   '>>>='
        |   '<<='
        |   '%='
        )
        expression                                                          # alt25Expression
    |   primary                                                             # alt26Expression
    ;

primary
    :   '(' expression ')'                                                              # alt1Primary
    |   'this'                                                                          # alt2Primary
    |   'super'                                                                         # alt3Primary
    |   literal                                                                         # alt4Primary
    |   id                                                                              # alt5Primary
    |   typeRef '.' 'class'                                                             # alt6Primary
    |   'void' '.' 'class'                                                              # alt7Primary
    |   nonWildcardTypeArguments (explicitGenericInvocationSuffix | 'this' arguments)   # alt8Primary
    |   soqlLiteral                                                                     # alt9Primary
    ;

creator
    :   nonWildcardTypeArguments createdName classCreatorRest                                   #alt1Creator
    |   createdName (arrayCreatorRest | classCreatorRest | mapCreatorRest | setCreatorRest)     #alt2Creator
    ;

createdName
    :   idCreatedNamePair ('.' idCreatedNamePair)*
    |   primitiveType
    ;

idCreatedNamePair
    :   id typeArgumentsOrDiamond?
    ;

innerCreator
    :   id nonWildcardTypeArgumentsOrDiamond? classCreatorRest
    ;

arrayCreatorRest
    :   '['
        (   ']' arraySubscripts arrayInitializer
        |   expression ']' ('[' expression ']')* arraySubscripts
        )
    ;

mapCreatorRest
    :   '{' mapCreatorRestPair (',' mapCreatorRestPair )* '}'
    ;

mapCreatorRestPair
    :   idOrExpression '=>' literalOrExpression
    ;

setCreatorRest
	: '{' literalOrExpression (',' ( literalOrExpression ))* '}'
	;

literalOrExpression
    :   literal | expression
    ;

idOrExpression
    :   id | expression
    ;

classCreatorRest
    :   arguments
    |   '{' expressionList? '}'
    ;

explicitGenericInvocation
    :   nonWildcardTypeArguments explicitGenericInvocationSuffix
    ;

nonWildcardTypeArguments
    :   '<' typeList '>'
    ;

typeArgumentsOrDiamond
    :   '<' '>'
    |   typeArguments
    ;

nonWildcardTypeArgumentsOrDiamond
    :   '<' '>'
    |   nonWildcardTypeArguments
    ;

superSuffix
    :   arguments
    |   '.' id arguments?
    ;

explicitGenericInvocationSuffix
    :   'super' superSuffix
    |   id arguments
    ;

arguments
    :   '(' expressionList? ')'
    ;


soqlLiteral
    : '[' (soqlLiteral|~']')*? ']'
    ;

withSharing
    : 'withsharing'
    | 'with' 'sharing'
    ;

withoutSharing
    : 'withoutsharing'
    | 'without' 'sharing'
    ;

// TODO: What is really reserved and do we care?
id
    :  Identifier
    |  'get'
    |  'set'

    // primitiveType
    |   'blob'
    |   'boolean'
    |   'date'
    |   'datetime'
    |   'decimal'
    |   'double'
    |   'id'
    |   'integer'
    |   'long'
    |   'object'
    |   'string'
    |   'time'

    |  'select'
    |  'insert'
    |  'upsert'
    |  'update'
    |  'delete'
    |  'undelete'
    |  'merge'

    |  'new'
    |  'withsharing'
    |  'withoutsharing'
    |  'for'
    ;

