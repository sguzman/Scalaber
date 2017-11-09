package com.github.sguzman.scala.scalaber.jsontypecheck.statement

import java.util

sealed case class UberRequest(
                             uri: UberURI,
                             method: String,
                             headers: util.HashMap[String,String]
                             )
