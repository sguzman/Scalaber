package com.github.sguzman.scala.scalaber.cmd

import com.beust.jcommander.Parameter

class Args {
  @Parameter(
    description = "Uber cookie"
  )
  var cookie: String = ""

  @Parameter(
    names = Array("-v", "--verbose"),
    description = "Level of verbosity",
    arity = 1,
    help = false,
    password = false,
    validateWith = Array(classOf[Validate])
  )
  var verbosity: String = "0"
}