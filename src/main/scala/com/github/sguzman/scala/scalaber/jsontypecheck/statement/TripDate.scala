package com.github.sguzman.scala.scalaber.jsontypecheck.statement

import java.util.UUID

sealed case class TripDate(
                          date: String,
                          cash_collected_total: Double,
                          total: Double,
                          total_earned: Double,
                          trips: Array[UUID]
                          )