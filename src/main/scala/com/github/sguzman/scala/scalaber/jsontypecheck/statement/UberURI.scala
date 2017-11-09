package com.github.sguzman.scala.scalaber.jsontypecheck.statement

import java.net.URI

sealed case class UberURI(
                           protocol: String,
                           slashes: Boolean,
                           auth: String,
                           host: String,
                           port: String,
                           hostname: String,
                           hash: String,
                           search: String,
                           query: String,
                           pathname: String,
                           path: String,
                           href: URI
                         )
