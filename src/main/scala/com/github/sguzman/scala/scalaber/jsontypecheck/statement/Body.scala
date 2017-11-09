package com.github.sguzman.scala.scalaber.jsontypecheck.statement

sealed case class Body(
                      driver_summary_stats: DriverSummaryStats,
                      misc_fees_breakdown: String,
                      driver: Driver,
                      cash_collected: Double,
                      total_earned: Double,
                      cash_summary: String,
                      partner: Partner,
                      total: Double,
                      city: City,
                      misc_non_fee_breakdown: MiscNonFeeBreakdown,
                      starting_at: String,
                      trip_earnings: TripEarnings1,
                      trips_count: Int,
                      ending_at: String,
                      currency_code: String
                      )
