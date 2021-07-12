package com.nawforce.pkgforce.stream

import com.nawforce.pkgforce.diagnostics.IssueLogger
import com.nawforce.pkgforce.names.Name
import com.nawforce.runtime.parsers.{PageParser, VFParser}

abstract class VFEvent(controllers: Array[Name],
                       components: Array[Name],
                       expressions: Array[String])
    extends PackageEvent

object VFEvent {
  def extractControllers(parser: PageParser,
                         logger: IssueLogger,
                         component: VFParser.VfUnitContext): Array[Name] = {
    Array()
  }

  def extractComponents(parser: PageParser,
                        logger: IssueLogger,
                        component: VFParser.VfUnitContext): Array[Name] = {
    Array()
  }

  def extractExpressions(parser: PageParser,
                         logger: IssueLogger,
                         component: VFParser.VfUnitContext): Array[String] = {
    Array()
  }
}
