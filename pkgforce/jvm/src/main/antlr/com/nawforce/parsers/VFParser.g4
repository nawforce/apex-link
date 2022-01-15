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

vfUnit: (COMMENT | WS_NL | processingInstruction)* element (COMMENT | WS_NL)* EOF;

element:
  OPEN Name attribute* CLOSE content OPEN SLASH Name CLOSE
  | OPEN Name attribute* SLASH_CLOSE
  | OPEN_SCRIPT attribute* CLOSE_OPEN_SCRIPT scriptChardata END_SCRIPT
  | OPEN_SCRIPT attribute* CLOSE_SCRIPT;

attribute:
  attributeName ATTRD_START attributeValues* ATTRD_END
  | attributeName ATTRS_START attributeValues* ATTRS_END
  | attributeName SCRIPT_ATTRD_START attributeValues* ATTRD_END
  | attributeName SCRIPT_ATTRS_START attributeValues* ATTRS_END;

attributeName:
  Name | ScriptName;

attributeValues:
    ATTRD_TEXT
    | ATTRS_TEXT
    | ATTRD_EL_START EL_BODY? EL_END
    | ATTRS_EL_START EL_BODY? EL_END;

content:
  chardata ((COMMENT | processingInstruction | element) chardata)*;

chardata:
  (TEXT
  | WS_NL
  | CHARDATA_REF
  | EL_START EL_BODY? EL_END
  | CDATA_START (CDATA_TEXT | CDATA_EL EL_BODY? EL_END)* CDATA_END)*;

processingInstruction:
  PI_START attribute* PI_END;

scriptChardata:
  (SCRIPT_TEXT
  | SCRIPT_WS_NL
  | SCRIPT_CHARDATA_REF
  | SCRIPT_EL_START EL_BODY? EL_END)*;






