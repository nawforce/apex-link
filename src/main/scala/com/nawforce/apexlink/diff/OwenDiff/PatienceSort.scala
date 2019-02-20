package com.nawforce.apexlink.diff.OwenDiff

import scala.collection.immutable.HashMap

object PatienceSort {
  // Find the longest increasing subsequence
  def LIS[A](source: Traversable[A])(implicit ev$1: A => Ordered[A]): Traversable[A] = {
    // Take a list of pile tops, a hashmap of backpointers
    // and an element. Add the element to the piles and backpointers.
    def createBackPointers(pileAndBackPointers: (List[A],
      HashMap[A, Option[A]]), elem: A) = {
      val (pileTops, backPointers) = pileAndBackPointers

      // Find the index at which the elem would be added
      // into pileTops.
      val index = bisect(pileTops, elem)

      // Add the element at that index
      val newPileTops = if (index == pileTops.length) {
        pileTops :+ elem
      } else {
        pileTops.updated(index, elem)
      }

      // If this isn't the first element, its backpointer
      // is the elem at the prior index.
      val newBPTuple =
      (elem, if (index > 0) Some(pileTops(index - 1)) else None)
      val newBackPointers = backPointers + newBPTuple

      (newPileTops, newBackPointers)
    }

    // Fold over the input list, creating backpointers and pile tops.
    val (pileTops, backPointers) =
      ((List[A](), new HashMap[A, Option[A]]) /: source) (createBackPointers)

    // Accumulate an increasing list of values, by following the chain of
    // backpointers, starting at the last value.
    def followPointers(current: Option[A], acc: List[A]): List[A] = {
      current match {
        case Some(v) => followPointers(backPointers(v), v +: acc)
        case _ => acc
      }
    }

    followPointers(pileTops.lastOption, Nil)
  }

  // Calculate the insertion position of elem in elems,
  // using a binary search.
  def bisect[A](elems: Seq[A], elem: A,
                lo: Int = 0, hi: Option[Int] = None)(implicit ev$1: A => Ordered[A]): Int = {
    if (lo < 0) {
      throw new IllegalArgumentException("Lower threshold out of range")
    }

    val high = hi match {
      case Some(v) => v
      case _ => elems.length
    }

    if (lo >= high) {
      lo
    } else {
      val mid = (lo + high) / 2

      val (newLow, newHigh) = if (elems(mid) < elem) {
        (mid + 1, high)
      } else {
        (lo, mid)
      }

      bisect(elems, elem, newLow, Some(newHigh))
    }
  }
}
