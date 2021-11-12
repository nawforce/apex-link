package com.nawforce.pkgforce.diagnostics

object Duplicates {

  implicit class IterableOps[A](iterable: Iterable[A]) {

    /** Return duplicates keyed against the first discovered. */
    def duplicates[K](f: A => K): Map[A, Iterable[A]] = {
      iterable
        .groupBy(f)
        .collect { case (_, group) if group.size > 1 => group }
        .map(group => group.head -> group.tail)
        .toMap
    }
  }
}
