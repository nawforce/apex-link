package io.github.nahforce.apexlink

import java.nio.file.{Files, Paths}

import io.github.nahforce.apexlink.metadata._
import io.github.nahforce.apexlink.transform.AssertDelete
import io.github.nahforce.apexlink.utils.LinkerLog

object Link {
  private val usage =
    """
      |Usage -transform <transform> <directory>
    """.stripMargin

  def main(args: Array[String]): Unit = {
    System.exit(link(args))
  }

  def link(args: Array[String]): Integer = {
    type OptionMap = Map[String, Any]

    class Options {
      var transforms : List[String] = List[String]()
      var unknown : List[String] = List[String]()
    }

    def nextOption(options: Options, list: List[String]): Options = {
      list match {
        case Nil => options
        case "-transform" :: value :: tail => options.transforms = value :: options.transforms; nextOption(options, tail)
        case value :: tail => options.unknown = value :: options.unknown; nextOption(options, tail)
      }
    }

    val options = nextOption(new Options(), args.toList)
    if (options.unknown.length != 1) {
      println(usage)
      return 1
    }
    val directory = Paths.get(options.unknown.head)
    if (!Files.isDirectory(directory)) {
      println("There is no directory at " + directory)
      println(usage)
      return 1
    }

    // Load from passed directory
    println("Loading from " + directory)
    val ctx = new SymbolReaderContext(directory)
    new LabelReader().loadSymbols(ctx)
    new CustomObjectReader().loadSymbols(ctx)
    // TODO: Re-enable page reading with HTML parser
    //new PageReader().loadSymbols(ctx)
    new ApexClassReader().loadSymbols(ctx)
    ctx.report()

    if (LinkerLog.hasMessages) {
      println("Problems found during linking, aborting run")
      LinkerLog.dumpMessage()
      return 1
    }

    // Run any transforms
    options.transforms.foreach(transform => {
      println("Running transform " + transform)
      transform match {
        case "assert-delete" => new AssertDelete().exec(ctx)
        case _ =>
          println("There is no transform " + transform)
          return 1
      }
    })
    return 0
  }
}
