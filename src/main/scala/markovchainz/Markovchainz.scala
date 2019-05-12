package markovchainz

import cats.implicits._

import java.io.InputStream

trait Markovchainz {
  import Markov._
  import TweetParser._

  def generateFullAffixMap(is: InputStream): Map[Prefix, Set[Suffix]] = {
    val cleanedTweets = cleanedTweetsFromCsv(is)

    cleanedTweets.map { tweet =>
      // tokenize the tweet
      val tokenized: List[String] = tokenize(tweet.text)

      // break up tokenized tweet sentence into triplets with sliding window groups
      val triplets: List[Triplet] = makeTriplets(tokenized)

      // create affix map keyed by Prefixes (first two words in a triplet),
      // values are Sets of Suffixes (last word in a triplet that follows a Prefix)
      val affixesMap: Map[Prefix, Set[Suffix]] = makeAffixMap(triplets)

      affixesMap
    } reduce { (m1, m2) =>
      m1.combine(m2) // merge all generated affix maps with cats semigroup combine for maps
    }
  }
}

object Markovchainz extends Markovchainz
