package com.nawforce.runtime.gc


object Monitor {
  val map = new SkinnyWeakSet[AnyRef]()

  def push[T <: AnyRef](t: T): Unit = {
    map.add(t)
  }

  def size: Int = map.size
}
