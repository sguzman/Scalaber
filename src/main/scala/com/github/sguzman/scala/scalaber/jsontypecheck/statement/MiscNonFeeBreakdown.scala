package com.github.sguzman.scala.scalaber.jsontypecheck.statement

sealed case class MiscNonFeeBreakdown(
                                     total: Double,
                                     items: Array[Item]
                                     )
