/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

import com.nawforce.apexlink.api.*;
import com.nawforce.apexlink.api.Package;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class BatchFind {

    public static void main(String[] args) throws IOException {
        if (args.length != 1 || !Files.isDirectory(Paths.get(args[0]))) {
            System.err.println("BatchFind <metadata directory>");
            System.exit(-1);
        }

        // Create Org to load metadata into
        ServerOps.setAutoFlush(false);
        Org org = Org.newOrg(true);

        // Load sfdx project in to Org
        Path metadata = Paths.get(args[0]);
        Path sfdxProject = metadata.resolve("sfdx-project.json");
        if (!Files.isRegularFile(sfdxProject)) {
            System.err.println("No sfdx-project.json file found in " + metadata.toString());
            System.exit(-1);
        }
        System.out.println("Loading project, this may take some time...");
        Package pkg = org.newSFDXPackage(metadata.toString());

        // Check for errors
        String issues = org.getIssues(new IssueOptions());
        if (!issues.isEmpty())
            System.err.println("Issues found in project:\n" + issues);

        // Flush org to update cache and speed re-analysis
        org.flush();

        findBatchClasses(pkg, metadata);
    }

    private static void findBatchClasses(Package pkg, Path metadata) throws IOException {
        // Locate all class types, these are the outer classes
        TypeIdentifier[] classTypes = Files.walk(metadata)
                .filter(Files::isRegularFile)
                .filter((f) -> f.toString().endsWith(".cls"))
                .map((f) -> pkg.getTypeOfPath(f.toString()))
                .filter(Objects::nonNull).toArray(TypeIdentifier[]::new);

        // Iterate finding outer or inner batch classes
        for (TypeIdentifier classType: classTypes) {
            printBatchClasses(pkg, pkg.getSummaryOfType(classType));
        }
    }

    private static void printBatchClasses(Package pkg, TypeSummary summary) {
        if (isBatch(pkg, summary))
            System.out.println(summary.typeName());

        for (TypeSummary innerClass: summary.nestedTypes()) {
            printBatchClasses(pkg, innerClass);
        }
    }

    private static Boolean isBatch(Package pkg, TypeSummary summary) {
        if(summary == null)
            return false;

        // Use startsWith here as Batchable is a generic
        if (Arrays.stream(summary.interfaces()).anyMatch((t) -> t.toString().startsWith("Database.Batchable<")))
            return true;

        // Check super class
        if (summary.superClass().isDefined()) {
            TypeIdentifier id = pkg.getTypeIdentifier( summary.superClass().get());
            return isBatch(pkg, pkg.getSummaryOfType(id));
        } else {
            return false;
        }
    }
}
