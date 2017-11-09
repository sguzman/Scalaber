package com.github.sguzman.scala.scalaber.jsontypecheck.statement

import java.util.UUID

sealed case class Driver(
                        cash_collected: Double,
                        misc_fees_breakdown: String,
                        driver_summary_stats: DriverSummaryStats,
                        total_earned: Double,
                        contact_number: String,
                        last_name: String,
                        driver_uuid: UUID,
                        total: Double,
                        first_name: String,
                        misc_non_fee_breakdown: MiscNonFeeBreakdown,
                        picture_url: String,
                        trip_earnings: TripEarnings2,
                        earnings: Earnings,
                        email: String
                        )
