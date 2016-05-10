package markovchainz

import java.io.File
import java.util.concurrent._

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{ Twitter, TwitterFactory }

object Main extends App {
  import Markovchainz._
  import Markov._

  val fileName = args(0)

  def getEnvVar(name: String): String = sys.env.getOrElse(name, throw new Exception(s"env var $name missing"))

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

  def postTweetsForever(twitter: Twitter, initialDelaySeconds: Int, periodSeconds: Int) = {
    val ex = new ScheduledThreadPoolExecutor(1)

    val task = new Runnable {
      def run = {
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

    ex.scheduleAtFixedRate(task, initialDelaySeconds, periodSeconds, TimeUnit.SECONDS)
  }

  postTweetsForever(twitter, 1, 30 * 60)
}
