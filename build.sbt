name := "markov-chainz"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "net.tixxit" %% "delimited-core" % "0.7.0",
  "org.twitter4j" % "twitter4j" % "4.0.4",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test")
