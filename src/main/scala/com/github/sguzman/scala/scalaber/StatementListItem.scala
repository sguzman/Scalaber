package com.github.sguzman.scala.scalaber

import java.util.{Date, UUID}

case class StatementListItem(
                            partner_uuid: UUID,
                            status: String,
                            city_id: Int,
                            total: Double,
                            id: Int,
                            payout_type: String,
                            payee_type: String,
                            uuid: UUID,
                            currency_code: String,
                            ending_at: Date,
                            num_txns: Int
                            ) {

}
