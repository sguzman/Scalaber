package com.github.sguzman.scala.scalaber

import com.github.sguzman.scala.scalaber.jsontypecheck.StatementListObject
import com.google.gson.GsonBuilder
import com.mashape.unirest.http.{HttpResponse, JsonNode, Unirest}
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

    val cookie = args.head
    Logger.debug("About to check cookie...")

    if (this.checkCookie(cookie)) {
      Logger.debug("All is good - Moving to next step")
    } else {
      Logger.error("Cookie did not work - Failing gracefully")
      System.exit(2)
    }

    Logger.debug("Retrieving statement list...")
    val list = this.getStatementList(cookie)
    if (list.isEmpty) {
      Logger.error("Something went wrong - Failing gracefully")
      System.exit(3)
    } else {
      Logger.info("Successfully retrieved summarized statement list")
    }

    Logger.debug("Retrieving statement objects")
    val statement = list.map(this.getTrips(cookie, _))
    if (statement.isEmpty) {
      Logger.error("Failed to retrieve list - Quitting...")
      System.exit(4)
    } else {
      Logger.info("Statement list returned")
    }
  }

  def get(url: String, cookie: String): HttpResponse[JsonNode] = {
    Unirest.get(url)
      .header("Cookie", cookie)
      .asJson
  }

  def checkArgsMessage: String = {
    "usage: <cookie>\n"
  }

  def checkArgs(args: Array[String]): Boolean = {
    args.length == 1
  }

  def checkCookie(cookie: String): Boolean = {
    try {
      val checkCookieURL = "https://partners.uber.com/p3/platform_chrome_nav_data"
      val resp = this.get(checkCookieURL, cookie)

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
      case _: Throwable =>
        false
    }
  }

  def getStatementList(cookie: String): IndexedSeq[StatementListObject] = {
    val getStatementListURL = "https://partners.uber.com/p3/money/statements/all_data"
    val resp = this.get(getStatementListURL, cookie)

    val gson = new GsonBuilder().create()

    val array = resp.getBody.getArray
    (0 until array.length).map(array.getJSONObject).map(_.toString).map(gson.fromJson(_, classOf[StatementListObject]))
  }

  def getTrips(cookie: String, uuid: String): IndexedSeq[AnyRef] = {
    val getStatementURL = s"https://partners.uber.com/p3/money/statements/view/$uuid"

    val resp = Unirest.get(getStatementURL)
      .header("Cookie", cookie)
      .asJson

    val array = resp.getBody.getObject.getJSONObject("driver").getJSONObject("trip_earnings").getJSONArray("trips")

    (0 until array.length).map(array.get)
  }
}
