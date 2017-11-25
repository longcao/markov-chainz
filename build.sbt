name := "markov-chainz"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

enablePlugins(JavaAppPackaging)

mappings in Universal += (file("tweets.csv") -> "bin/tweets.csv")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.5.0",
  "net.tixxit" %% "delimited-core" % "0.7.0",
  "org.twitter4j" % "twitter4j-core" % "4.0.6",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test")
