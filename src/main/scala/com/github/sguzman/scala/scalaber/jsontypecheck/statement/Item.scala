package com.github.sguzman.scala.scalaber.jsontypecheck.statement

sealed case class Item(
                      recognized_at: String,
                      total: Double,
                      is_carrot_incentive: Boolean,
                      description: String
                      )