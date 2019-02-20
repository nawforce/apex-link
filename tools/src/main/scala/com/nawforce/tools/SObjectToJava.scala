
package com.nawforce.tools

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.Paths

import com.nawforce.apexlink.utils.SFConnection


object SObjectToJava {
  def loginURL = "https://login.salesforce.com/services/Soap/u/45.0"

  def main(args: Array[String]): Unit = {

    args.length match {
      case 2 => ()
      case _ => println("Usage: SObjectToJava <username> <password>"); return
    }

    val connectionResult = SFConnection.login(loginURL, args(0), args(1))
    if (connectionResult.isLeft) {
      println(connectionResult.left.get)
      return
    }
    val partnerConnection = connectionResult.right.get.connection
    val sObjectNames = partnerConnection.describeGlobal().getSobjects.filterNot(
      dfr => dfr.getCustom || dfr.getCustomSetting
    ).map(_.getName)

    sObjectNames.grouped(100).foreach(grp => {
      partnerConnection.describeSObjects(grp).foreach(sobjectDescribe => {
        val describedSObject = DescribedSObject(sobjectDescribe)

        val output = Paths.get(describedSObject.name+".java").toFile
        val writer = new BufferedWriter(new FileWriter(output))
        writer.write(describedSObject.asJava)
        writer.flush()
        writer.close()
      })
    })
  }
}
