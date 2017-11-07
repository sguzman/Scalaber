package com.github.sguzman.scala.scalaber

import com.mashape.unirest.http.{HttpResponse, JsonNode, Unirest}
import com.mashape.unirest.request.HttpRequest
import org.pmw.tinylog.Logger

object Scalaber {
  val app = "Scalaber"

  def main(args: Array[String]): Unit = {
    Logger.info("Starting {} application - Hello world!", this.app)
    Logger.debug("Checking args...")
    Logger.info(s"Args is ${args.mkString("[", ", ", "]")}")
    if (this.checkArgs(args)) {
      Logger.debug("Args is good")
    } else {
      Logger.error("Args is bad - Quitting...")
      System.exit(1)
    }

    Logger.debug("About to check cookie...")
    if (this.checkCookie(args.head)) {
      Logger.debug("All is good - Moving to next step")
    } else {
      Logger.error("Cookie did not work - Failing gracefully")
      System.exit(2)
    }

  }

  def checkArgsMessage: String = {
    "usage: <cookie>\n"
  }

  def checkArgs(args: Array[String]): Boolean = {
    args.length == 1
  }

  def checkCookie(str: String): Boolean = {
    try {
      val checkCookieURL = "https://partners.uber.com/p3/platform_chrome_nav_data"

      val resp = Unirest.get(checkCookieURL)
        .header("Cookie", str)
        .asJson

      val betterBe2 =
        resp
          .getBody
          .getObject
          .get("nav").asInstanceOf[org.json.JSONObject]
          .getJSONObject("payments")
          .getJSONObject("nav")
          .getJSONObject("taxes")
          .getJSONArray("urls")
          .length

      val status = resp.getStatus == 200 && resp.getStatusText == "OK" && betterBe2 == 2

      status
    } catch {
      case ex: Throwable =>
        false
    }
  }
}
