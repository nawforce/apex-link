/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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

export interface InfoMessages {
  files: FileMessage[];
}

interface FileMessage {
  path: string;
  messages: PositionalMessage[];
}

interface PositionalMessage {
  start: Position;
  end: Position;
  message: string;
}

interface Position {
  line: number;
  offset: number;
}

export class MessageWriter {
  private buffer: string = "";

  public constructor() {}

  public output(): string {
    return this.buffer;
  }

  public writeMessages(messages: InfoMessages) {
    for (const fileMessage of messages.files) {
      this.writeFileMessage(fileMessage);
    }
  }

  public writeFileMessage(fileMessage: FileMessage) {
    this.buffer += fileMessage.path + "\n";
    for (const positionalMessage of fileMessage.messages) {
      this.writePositionalMessage(positionalMessage);
    }
  }

  public writePositionalMessage(positionalMessage: PositionalMessage) {
    this.writePosition(positionalMessage.start, positionalMessage.end);
    this.buffer += `: ${positionalMessage.message}\n`;
  }

  public writePosition(start: Position, end: Position) {
    if (
      typeof start.offset !== "undefined" &&
      typeof end !== "undefined" &&
      typeof end.offset !== "undefined"
    ) {
      if (start.line === end.line) {
        this.buffer += `line ${start.line} at ${start.offset}-${end.offset}`;
      } else {
        this.buffer += `line ${start.line} to ${end.line}`;
      }
    } else {
      this.buffer += `line ${start.line}`;
    }
  }
}
