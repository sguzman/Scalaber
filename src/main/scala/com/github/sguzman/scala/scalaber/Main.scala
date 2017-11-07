package com.github.sguzman.scala.scalaber

import com.mashape.unirest.http.{HttpResponse, JsonNode, Unirest}
import com.mashape.unirest.request.HttpRequest
import org.pmw.tinylog.Logger

object Main {
  val app = "Scalaber"

  def main(args: Array[String]): Unit = {
    Logger.info("Starting {} application - Hello world!", this.app)
    println(this.checkCookie(args.head))
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

      resp.getStatus == 200 && resp.getStatusText == "OK" && betterBe2 == 2
    } catch {
      case _: Throwable => false
    }
  }
}
