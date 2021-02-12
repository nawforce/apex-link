/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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

lexer grammar VFLexer;

@lexer::members {
    public void clearCache() {
        _interp.clearDFA();
    }
}

channels {
    WHITESPACE_CHANNEL,
    COMMENT_CHANNEL
}

COMMENT:  		'<!--' .*? '-->';
DOCTYPE:      '<!DOCTYPE' .*? '>';
DECL_START:  	'<?' Name WS? -> pushMode(INSIDE);
OPEN:         '<' -> pushMode(INSIDE);
EL_START:     '{!';
EL_END:       '}';

EntityRef:    '&' Name ';';
CharRef:      '&#' [0-9]+ ';'
              | '&#x' [a-fA-F0-9]+ ';';

WS_NL:        (' '|'\t'|'\r'? '\n')+ ;
TEXT:         ~[<&]+;

mode INSIDE;

DECL_END:  	  '?>' -> popMode;
CLOSE:        '>' -> popMode;
SLASH_CLOSE:  '/>' -> popMode;

SLASH:        '/';

EQUALS:       '=';
STRING      :   '"' ~[<"]* '"'
            |   '\'' ~[<']* '\'';

WS:           [ \t\r\n] -> skip;

Name:         NameStartChar NameChar*;

fragment
NameChar    :   NameStartChar
            |   '-' | '_' | '.' | [0-9]
            |   '\u00B7'
            |   '\u0300'..'\u036F'
            |   '\u203F'..'\u2040'
            ;

fragment
NameStartChar
            :   [:a-zA-Z]
            |   '\u2070'..'\u218F'
            |   '\u2C00'..'\u2FEF'
            |   '\u3001'..'\uD7FF'
            |   '\uF900'..'\uFDCF'
            |   '\uFDF0'..'\uFFFD'
            ;


