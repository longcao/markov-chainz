package markovchainz

import java.io.File

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{ Twitter, TwitterFactory }

object Main {
  import Markovchainz._
  import Markov._

  def getEnvVar(name: String): String =
    sys.env.getOrElse(name, throw new Exception(s"env var $name missing"))
  
  def main(args: Array[String]): Unit = {
    val fileName = args(0)


    val twitterConsumerKey       = getEnvVar("twitterConsumerKey")
    val twitterConsumerSecret    = getEnvVar("twitterConsumerSecret")
    val twitterAccessToken       = getEnvVar("twitterAccessToken")
    val twitterAccessTokenSecret = getEnvVar("twitterAccessTokenSecret")

    val cb = new ConfigurationBuilder()
      .setOAuthConsumerKey(twitterConsumerKey)
      .setOAuthConsumerSecret(twitterConsumerSecret)
      .setOAuthAccessToken(twitterAccessToken)
      .setOAuthAccessTokenSecret(twitterAccessTokenSecret)

    val twitter = new TwitterFactory(cb.build()).getInstance

    val megaMap = generateFullAffixMap(new File(fileName))

    val chain = wordsToSentence(walkChain(megaMap))

    try {
      val status = twitter.updateStatus(chain)

      println(s"Chain: $chain")
      println(s"Posted status: ${status.getId}")
      println()
    } catch {
      case ex: Exception => println(ex.getMessage)
    }
  }
}
