package com.github.sguzman.scala.scalaber.jsontypecheck.trip

import java.util.UUID

sealed case class Trip(
                        dropoffAddress: String,
                        marketplace: String,
                        licensePlate: String,
                        customRouteMap: String,
                        totalToll: String,
                        cashCollected: String,
                        timezone: String,
                        duration: Double,
                        pickupAddress: String,
                        total: Double,
                        vehicleType: String,
                        fareAdjustmentReasons: Array[String],
                        currencyCode: String,
                        chainUuid: UUID,
                        legs: Array[String],
                        requestAt: Long,
                        breakdown: Array[BreakdownItem],
                        isSurge: Boolean,
                        isPoolType: Boolean,
                        partnerUuid: UUID,
                        routeMap: String,
                        uuid: UUID,
                        distance: Double,
                        fareAdjustmentMessages: Array[String],
                        bankDeposit: String,
                        status: String,
                        disclaimer: String
                      )
