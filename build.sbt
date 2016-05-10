name := "markov-chainz"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

enablePlugins(JavaAppPackaging)

mappings in Universal += (file("tweets.csv") -> "bin/tweets.csv")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.5.0",
  "net.tixxit" %% "delimited-core" % "0.7.0",
  "org.twitter4j" % "twitter4j-core" % "4.0.4",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test")
