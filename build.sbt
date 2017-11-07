/** Name of project */
name := "Scalaber"

/** Project Version */
version := "1.0"

/** Scala version */
scalaVersion := "2.12.4"

/** Scalac parameters */
scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

/** Javac parameters */
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

/** Resolver */
resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Search Maven" at "https://repo1.maven.org/maven2/"
)

/** Source Dependencies */
libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.12" % "3.2.0-SNAP9",
  "org.tinylog" % "tinylog" % "1.3-rc-2",
  "com.mashape.unirest" % "unirest-java" % "1.4.9",
  "com.lihaoyi" % "upickle_2.12" % "0.4.4"
)

/** Make sure to fork on run */
fork in run := true