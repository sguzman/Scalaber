package com.github.sguzman.scala.scalaber.jsontypecheck.statement

import java.util
import java.util.UUID

sealed case class TripEarnings2(
                                 trip_dates: Array[TripDate],
                                 cash_collected: Double,
                                 rates: String,
                                 fee_types: FeeTypes,
                                 eligible_earnings: Double,
                                 total: Double,
                                 total_earned: Double,
                                 trips: util.HashMap[UUID, Trip],
                                 totals: Totals
                               )
