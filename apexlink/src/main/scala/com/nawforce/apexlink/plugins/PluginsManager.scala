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
package com.nawforce.apexlink.plugins

import com.nawforce.apexlink.plugins.PluginsManager.activePlugins
import com.nawforce.apexlink.types.core.DependentType

import java.lang.reflect.Constructor
import scala.collection.mutable

/** Manage the lifecycle of plugins. Uses a create/close model to allow block analysis close in time to normal
  * validation to limit GC costs while also supporting type level analysis after all types have been validated.
  * Plugins may dynamically add additional types for analysis as part of the close handling.
  */
class PluginsManager {
  private val availablePlugins = activePlugins()
  private val livePlugins      = new mutable.HashMap[DependentType, Option[Plugin]]()

  /** Create a new plugin dispatcher for a DependentType. */
  def createPlugin(td: DependentType): Plugin = {
    val plugin = PluginDispatcher(td, availablePlugins)
    livePlugins.put(td, Some(plugin))
    plugin
  }

  /** Close all open plugins. */
  def closePlugins(): Unit = {
    val toClose = livePlugins.filter(_._2.nonEmpty)
    toClose.keys.foreach(dt => livePlugins.put(dt, None))

    val additional = toClose.flatMap(_._2.get.onTypeValidated())
    additional.foreach(td => {
      if (!livePlugins.contains(td)) {
        livePlugins.put(td, Some(PluginDispatcher(td, availablePlugins)))
      }
    })

    if (livePlugins.exists(_._2.nonEmpty)) {
      closePlugins()
    } else {
      livePlugins.clear()
    }
  }
}

object PluginsManager {
  private val defaultPlugins     = Seq[Class[_ <: Plugin]](classOf[UnusedPlugin])
  private var plugins            = defaultPlugins
  private var pluginConstructors = defaultPlugins.map(_.getConstructor(classOf[DependentType]))

  def activePlugins(): Seq[Constructor[_ <: Plugin]] = pluginConstructors

  def overridePlugins(newPlugins: Seq[Class[_ <: Plugin]]): Seq[Class[_ <: Plugin]] = {
    val current = plugins
    setPlugins(newPlugins)
    current
  }

  def removePlugins(removePlugins: Seq[Class[_ <: Plugin]]): Seq[Class[_ <: Plugin]] = {
    val current = plugins
    setPlugins(plugins.filterNot(removePlugins.contains))
    current
  }

  private def setPlugins(newPlugins: Seq[Class[_ <: Plugin]]): Unit = {
    plugins = newPlugins
    pluginConstructors = plugins.map(_.getConstructor(classOf[DependentType]))
  }

}
