package markovchainz

import java.io.File

import TweetParser._

object Markovchainz extends App {
  val fileName = args(0)

  val cleanedTweets = cleanedTweetsFromCsv(new File(fileName))

  cleanedTweets.foreach(println)
}
