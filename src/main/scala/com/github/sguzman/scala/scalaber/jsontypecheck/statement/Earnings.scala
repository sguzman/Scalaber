package com.github.sguzman.scala.scalaber.jsontypecheck.statement

sealed case class Earnings(
                          cash_collected: Double,
                          total: Double,
                          total_earned: Double,
                          totals: Totals
                          )