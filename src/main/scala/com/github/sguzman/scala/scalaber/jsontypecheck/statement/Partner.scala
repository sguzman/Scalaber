package com.github.sguzman.scala.scalaber.jsontypecheck.statement

import java.util.UUID

sealed case class Partner(
                         status: Int,
                         contact_number: String,
                         last_name: String,
                         uuid: UUID,
                         city_id: Int,
                         picture_url: String,
                         country_id: Int,
                         first_name: String,
                         id: String,
                         token: String,
                         base_number: String,
                         flow_type: Int,
                         language_code: String,
                         referral_code: String,
                         partner_user_uuid: UUID,
                         email: String
                         )