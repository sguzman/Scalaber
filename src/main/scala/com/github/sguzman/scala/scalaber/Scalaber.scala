package com.github.sguzman.scala.scalaber

import java.util.UUID

import com.beust.jcommander.JCommander
import com.github.sguzman.scala.scalaber.cmd.Args
import com.github.sguzman.scala.scalaber.jsontypecheck.StatementListObject
import com.github.sguzman.scala.scalaber.jsontypecheck.statement.Statement
import com.github.sguzman.scala.scalaber.jsontypecheck.trip.Trip
import com.google.gson.GsonBuilder
import com.mashape.unirest.http.{HttpResponse, JsonNode, Unirest}
import org.pmw.tinylog.{Configurator, Level, Logger}

object Scalaber {
  val app = "Scalaber"

  def main(args: Array[String]): Unit = {
    val argv = this.checkArgs(args)
    if (argv.isEmpty) {
      Logger.error("Args is bad - Quitting...")
      System.exit(1)
    } else {
      val level = this.getLogLevel(argv.get.verbosity)
      Configurator
        .currentConfig()
        .level(level)
        .activate()
      Logger.debug("Args is good")
    }

    Logger.info("Starting {} application - Hello world!", this.app)
    Logger.debug("Checking args...")
    Logger.info(s"Args is ${args.mkString("[", ", ", "]")}")

    val validArgs = argv.get

    val cookie = validArgs.cookie
    Logger.info("About to check cookie...")

    if (this.checkCookie(cookie)) {
      Logger.debug("All is good - Moving to next step")
    } else {
      Logger.error("Cookie did not work - Failing gracefully")
      System.exit(2)
    }

    Logger.info("Retrieving statement list...")
    val list = this.getStatementList(cookie)
    if (list.isEmpty) {
      Logger.error("Something went wrong - Failing gracefully")
      System.exit(3)
    } else {
      Logger.info("Successfully retrieved summarized statement list")
      Logger.debug(list.mkString("[", ", ", "]"))
    }

    val getStmt = this.getStatementObject(cookie, _: UUID)

    Logger.info("Retrieving statement objects")
    val statements = list.map(_.uuid).par.map(getStmt)
    if (statements.isEmpty) {
      Logger.error("Failed to retrieve list - Quitting...")
      System.exit(4)
    } else {
      Logger.info("Statement list returned")
      Logger.debug(statements.mkString("[", ", ", "]"))
    }

    val getTrip = this.getTrip(cookie, _: UUID)

    Logger.info("Retrieving trips")
    val trips = statements
      .map(_.body.driver.trip_earnings.trips.keySet)
      .flatMap(_.toArray)
      .map(_.asInstanceOf[UUID])
      .par
      .map(getTrip)

    if (trips.isEmpty) {
      Logger.error("Failed to retrieve trips")
      System.exit(5)
    } else {
      Logger.info("Successfully retrieved trips")
      val gson = new GsonBuilder().create()
      println(trips.map(gson.toJson).mkString("{", ", ", "}"))
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

  def checkArgs(args: Array[String]): Option[Args] = {
    try {
      if (args.length == 1 || args.length == 3) {
        val main = new Args()
        JCommander.newBuilder().addObject(main).build().parse(args: _*)
        Some(main)
      } else {
        None
      }
    } catch {
      case _: Throwable => None
    }
  }

  def getLogLevel(level: String): Level = level match {
    case "0" => Level.OFF
    case "1" => Level.TRACE
    case "2" => Level.DEBUG
    case "3" => Level.INFO
    case "4" => Level.WARNING
    case "5" => Level.ERROR
    case _ => Level.DEBUG
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

  def getStatementObject(cookie: String, uuid: UUID): Statement = {
    val getStatementURL = s"https://partners.uber.com/p3/money/statements/view/${uuid.toString}"
    val resp = this.get(getStatementURL, cookie).getBody.getObject

    val gson = new GsonBuilder().create()
    gson.fromJson(resp.toString, classOf[Statement])
  }

  def getTrip(cookie: String, uuid: UUID): Trip = {
    val getTripURL = s"https://partners.uber.com/p3/money/trips/trip_data/${uuid.toString}"
    val resp = this.get(getTripURL, cookie).getBody.getObject

    val gson = new GsonBuilder().create()
    gson.fromJson(resp.toString, classOf[Trip])
  }
}
