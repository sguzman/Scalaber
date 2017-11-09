package com.github.sguzman.scala.scalaber.jsontypecheck.statement

import java.util.UUID

sealed case class Trip(
                      fare: Double,
                      marketplace: String,
                      is_star_power: Boolean,
                      dropoff_at: String,
                      trip_chaining: TripChaining,
                      duration: Double,
                      total: Double,
                      trip_id: UUID,
                      `type`: String,
                      status: String,
                      uber_fee: Double,
                      cash_collected: Double,
                      total_earned: Double,
                      request_at: String,
                      begintrip_at: String,
                      date: String,
                      distance: Double,
                      is_cash_trip: Boolean,
                      join_and_support_eligible: Boolean,
                      currency_code: String
                      )