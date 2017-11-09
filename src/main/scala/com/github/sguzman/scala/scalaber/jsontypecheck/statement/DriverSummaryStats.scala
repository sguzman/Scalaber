package com.github.sguzman.scala.scalaber.jsontypecheck.statement

sealed case class DriverSummaryStats(
                                    completed_trips: Double,
                                    acceptance_rate: Double,
                                    online_hours: Double,
                                    rider_cancellations: Double,
                                    driver_cancellations: Double
                                    )