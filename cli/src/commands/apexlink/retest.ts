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

import { flags, SfdxCommand } from "@salesforce/command";
import { Messages, SfdxError } from "@salesforce/core";
import { AnyJson } from "@salesforce/ts-types";
import * as fs from "fs";
import * as moment from "moment";
import { CommandStatus } from "../../cmd/commandRunner";
import TestRunProgress from "../../cmd/testRunProgress";
import TestRunResults, { TestRunDetail } from "../../cmd/testRunResults";
import TestRunSingle from "../../cmd/testRunSingle";
import TestRunStart from "../../cmd/testRunStart";
import TestRunWait from "../../cmd/testRunWait";

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file. Messages from @salesforce/command, @salesforce/core,
// or any library that is using the messages framework can also be loaded this way.
const messages = Messages.loadMessages("apexlink", "retest");

// TODO: For future use
// const includedExtensions = [".cls", ".trigger"];

export default class ReTest extends SfdxCommand {
  public static description = messages.getMessage("commandDescription");

  public static examples = [
    `$ sfdx apexlink:retest`,
    `$ sfdx apexlink:retest --all --verbose`
  ];

  protected static flagsConfig = {
    all: flags.boolean({ description: "run all local tests NOTE: this enabled by default currently" }),
    json: flags.boolean({
      description: "show output in json format (disables --verbose)"
    }),
    verbose: flags.builtin({ description: "show progress messages" })
  };

  protected static requiresUsername = false;
  protected static supportsDevhubUsername = false;
  protected static requiresProject = true;

  protected updateProgressFrequency = 60000;
  private testRunId = "";

  public async run(): Promise<AnyJson> {
    const startMoment = moment();
    this.ux.log(`Starting parallel test run at ${startMoment.format("LLLL")}`);
    await new TestRunStart()
      .execute()
      .then(testRunId => {
        this.testRunId = testRunId;
      })
      .catch(status =>
        this.throwFailure("When starting parallel test run", status)
      );

    this.monitorProgress(startMoment);
    await new TestRunWait(this.testRunId)
      .execute()
      .then(async status => {
        const testRunId = this.testRunId;
        this.testRunId = "";
        this.ux.log(
          `parallel Test run completed at ${moment().format("LLLL")}`
        );
        const failures = await this.sequentialTestAndReport(testRunId);
        if (failures.length !== 0) {
          fs.writeFileSync(
            "test-results.json",
            JSON.stringify(failures, null, 4)
          );
          throw new SfdxError("Some tests failed, review test-results.json for failure details");
        }
        const timePassed = moment
          .duration(moment().diff(startMoment))
          .humanize();
        this.ux.log(
          `Finished at ${moment().format("LLLL")}, took ${timePassed}`
        );
      })
      .catch(status =>
        this.throwFailure("When waiting for parallel test run to end", status)
      );
    return {};
  }

  private throwFailure(context: string, status: CommandStatus) {
    if (status instanceof SfdxError) {
      throw status;
    } else if (status.stderr !== "") {
      throw new SfdxError(context + " " + JSON.parse(status.stderr).message);
    } else {
      throw new SfdxError(
        context + " " + `a bad status code was returned: ${status.statusCode}`
      );
    }
  }

  private async monitorProgress(startMoment: moment.Moment) {
    if (this.testRunId === "") return;
    setTimeout(
      () => this.monitorProgress(startMoment),
      this.updateProgressFrequency
    ).unref();

    const runCount = await new TestRunProgress(this.testRunId, false).execute();
    const failedCount = await new TestRunProgress(
      this.testRunId,
      true
    ).execute();
    const timePassed = moment.duration(moment().diff(startMoment)).humanize();
    this.ux.log(
      `${failedCount} tests have failed of ${runCount} run after ${timePassed}`
    );
  }

  private async sequentialTestAndReport(
    testRunId: string
  ): Promise<TestRunDetail[]> {
    const results = await new TestRunResults(testRunId).execute();
    const locked = results.filter(
      result =>
        result.Outcome !== "Pass" &&
        (result.Message.search(/UNABLE_TO_LOCK_ROW/) !== -1 ||
          result.Message.search(/deadlock detected/) !== -1)
    );
    if (locked.length > 0) {
      this.ux.log(
        `Of ${results.length} tests run, ${
          locked.length
        } failed due to locking issues, re-running these sequentially`
      );
      for (const result of locked) {
        const testName = result.ApexClass.Name + "." + result.MethodName;
        this.ux.log(`Running ${testName}`);
        const status = await new TestRunSingle(testName).execute();
        if (status.statusCode === 0) result.Outcome = "Pass";
      }
    }
    const failed = results.filter(result => result.Outcome !== "Pass");
    if (failed.length !== 0) {
      this.ux.log(
        `Of ${results.length} tests run, ${failed.length} have failed :-(`
      );
    } else {
      this.ux.log(`Of ${results.length} tests run, NONE have failed :-)`);
    }
    return failed;
  }

  /* TODO: For future use
  private async collectChangedFiles(): Promise<string[]> {
    const projectConfig = await this.project.resolveProjectConfig();
    const forceIgnore = new ForceIgnore(this.project.getPath(), projectConfig);

    return new GitStatus()
      .execute()
      .then((changedFiles: ChangedFile[]) => {
        return changedFiles
          .filter(fileStatus => {
            return (
              forceIgnore.isValid(fileStatus.path) &&
              includedExtensions.filter(ext => fileStatus.path.endsWith(ext))
                .length > 0
            );
          })
          .map(fileStatus => fileStatus.path);
      })
      .catch((status: CommandStatus) => {
        if (status.abortError)
          throw new SfdxError(
            messages.getMessage("errorNoStatus", [status.abortError])
          );
        else
          throw new SfdxError(
            messages.getMessage("errorBadStatus", [status.statusCode])
          );
      });
  }
  */
}
