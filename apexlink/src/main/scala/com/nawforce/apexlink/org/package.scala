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
package com.nawforce.apexlink

import com.nawforce.pkgforce.stream.{IssuesEvent, PackageEvent}

import scala.collection.immutable.ArraySeq
import scala.collection.{BufferedIterator, mutable}
import scala.reflect.ClassTag

package object org {

  /** Read the maximum events that are all from an accepting set into an Array. IssueEvents are silently consumed
    * and logged against the active org.
    */
  def bufferEvents(
    accept: Set[Class[_]],
    events: BufferedIterator[PackageEvent]
  ): ArraySeq[PackageEvent] = {
    val buffer   = mutable.ArrayBuffer[PackageEvent]()
    var continue = events.hasNext
    while (continue) {
      continue = events.head match {
        case _ if accept.contains(events.head.getClass) => buffer.append(events.head); true
        case event: IssuesEvent                         => event.issues.foreach(OrgImpl.log); true
        case _                                          => false
      }
      if (continue) {
        events.next()
        continue = events.hasNext
      }
    }
    ArraySeq.unsafeWrapArray(buffer.toArray)
  }

  /** Read the maximum events that are all of the given type into an Array. IssueEvents are silently consumed
    * and logged against the active org.
    */
  def bufferEvents[T: ClassTag](events: BufferedIterator[PackageEvent]): ArraySeq[T] = {
    val buffer   = mutable.ArrayBuffer[T]()
    var continue = events.hasNext
    while (continue) {
      continue = events.head match {
        case e: T           => buffer.append(e); true
        case e: IssuesEvent => e.issues.foreach(OrgImpl.log); true
        case _              => false
      }
      if (continue) {
        events.next()
        continue = events.hasNext
      }
    }
    ArraySeq.unsafeWrapArray(buffer.toArray)
  }
}
