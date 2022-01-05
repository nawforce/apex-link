/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
 */
package com.nawforce.pkgforce.api;

public abstract class IssueLocation {
    public abstract int startLineNumber();
    public abstract int startCharOffset();
    public abstract int endLineNumber();
    public abstract int endCharOffset();

    public String displayPosition() {
        if (startLineNumber() == 1 && endLineNumber() == Integer.MAX_VALUE && startCharOffset() == 0 && endCharOffset() == 0) {
            return "line 1";
        }
        if (startLineNumber() == endLineNumber()) {
            if (startCharOffset() == 0 && endCharOffset() == 0)
                return "line " + startLineNumber();
            else if (startCharOffset() == endCharOffset())
                return "line " + startLineNumber() + " at " + startCharOffset();
            else
                return "line " + startLineNumber() + " at " + startCharOffset() + "-" + endCharOffset();
        } else {
            if (startCharOffset() == 0 && endCharOffset() == 0)
                return "line " + startLineNumber() + " to " + endLineNumber();
            else
                return "line " + startLineNumber() + ":" + startCharOffset() + " to " + endLineNumber() + ":" + endCharOffset();
        }
    }

    public boolean contains(int line, int offset) {
        return !(line < startLineNumber() || line > endLineNumber() ||
                (line == startLineNumber() && offset < startCharOffset()) ||
                (line == endLineNumber() && offset > endCharOffset()));
    }

    public boolean contains(IssueLocation other) {
        return contains(other.startLineNumber(), other.startCharOffset()) &&
                contains(other.endLineNumber(), other.endCharOffset());
    }
}
