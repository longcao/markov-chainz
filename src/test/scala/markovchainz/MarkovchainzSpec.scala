package markovchainz

import cats.implicits._

import java.io.File

import org.scalactic.TypeCheckedTripleEquals

import org.scalatest.{ Matchers, WordSpec }

class MarkovchainzSpec extends WordSpec
  with Matchers
  with TypeCheckedTripleEquals { self =>

  import Markov._
  import Markovchainz._
  import TweetParser._

  private val fullTweetsFile = new File(self.getClass.getResource("/full-tweets.csv").toURI)

  "Full integration of markov chaining" should {
    "work properly given a full tweet dataset" in {
      val fullMap = generateFullAffixMap(fullTweetsFile)

      (0 to 1000).foreach { _ =>
        val chain = wordsToSentence(walkChain(fullMap))

        chain should not be empty
        chain.length should be <= 140
      }
    }
  }
}
