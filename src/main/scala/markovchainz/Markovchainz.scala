package markovchainz

import cats.implicits._

import java.io.File

trait Markovchainz {
  import Markov._
  import TweetParser._

  def generateFullAffixMap(file: File): Map[Prefix, Set[Suffix]] = {
    val cleanedTweets = cleanedTweetsFromCsv(file)

    cleanedTweets.map { tweet =>
      // tokenize the tweet
      val tokenized: List[String] = tokenize(tweet)

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

object Main extends App {
  import Markovchainz._
  import Markov._

  val fileName = args(0)

  val megaMap = generateFullAffixMap(new File(fileName))

  // todo: tweet scheduling here, for now print out a few generated tweets
  (0 to 50).foreach { _ =>
    val chain = wordsToSentence(walkChain(megaMap))

    println(chain)
    println
  }
}
