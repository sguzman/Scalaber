package com.github.sguzman.scala.scalaber.cmd

import com.beust.jcommander.{IParameterValidator, ParameterException}

class Validate extends IParameterValidator {
  override def validate(s: String, s1: String): Unit = s1 match {
    case "0" | "1" | "2" | "3" | "4" | "5" => ()
    case _ => throw new ParameterException(s"$s1 is not a valid logging level")
  }
}
