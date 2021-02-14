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

parser grammar VFParser;
options {tokenVocab=VFLexer;}

@parser::members {
    public void clearCache() {
        _interp.clearDFA();
    }
}

vfUnit: prolog? misc* element misc* EOF;

prolog:
  (COMMENT | WS_NL)* (declaration | DOCTYPE);

declaration:
  DECL_START attribute* DECL_END;

element:
  OPEN Name attribute* CLOSE content OPEN SLASH Name CLOSE
  | OPEN Name attribute* SLASH_CLOSE;

attribute:
  Name ATTRD_START attributeValues* ATTRD_END
  | Name ATTRS_START attributeValues* ATTRS_END;

attributeValues:
    ATTRD_TEXT
    | ATTRS_TEXT
    | ATTRD_EL_START EL_BODY? EL_END
    | ATTRS_EL_START EL_BODY? EL_END
    | ATTRD_REF
    | ATTRS_REF;

content:
  chardata ((COMMENT | element) chardata)*;

chardata:
  (TEXT
  | CHARDATA_REF
  | EL_START EL_BODY? EL_END)*;

 misc:
  COMMENT;





