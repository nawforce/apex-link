/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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

package io.github.nawforce;

import io.github.nawforce.apexlink.api.LinkContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ApexLink {

    public static void main(String[] args) {
        System.exit(new ApexLink().run(args));
    }

    static class Options {
        Boolean isVerbose = false;
        List<String> transforms = new ArrayList<>();
        List<String> unknowns = new ArrayList<>();
    }

    public static Integer run(String[] args) {
        Options options = parseOption(new LinkedList<>(Arrays.asList(args)), null);

        // Convert arguments to options
        if (options.unknowns.size() != 1) {
            System.err.println("Usage [-verbose] -transform <transform> <directory>");
            return 1;
        }
        Path directory = Paths.get(options.unknowns.get(0));
        if (!Files.isDirectory(directory)) {
            System.err.println("There is no directory at " + directory);
            return 1;
        }

        // Link from directory
        System.out.println("Loading from " + directory);
        LinkContext lc = LinkContext.create(directory, options.isVerbose);
        lc.report();

        // Run any transforms
        lc.transform(options.transforms);

        return 0;
    }

    private static Options parseOption(List<String> args, Options options) {
        if (options == null)
            options = new Options();

        if (args.isEmpty())
            return options;

        if (args.get(0).equals("-verbose")) {
            args.remove(0);
            options.isVerbose = true;
        } else if (args.size() > 1 && args.get(0).equals("-transform")) {
            args.remove(0);
            options.transforms.add(args.remove(0));
        } else {
            options.unknowns.add(args.remove(0));
        }
        return parseOption(args, options);
    }
}
