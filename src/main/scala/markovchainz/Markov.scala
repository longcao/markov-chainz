package markovchainz

sealed trait Affix
case class Prefix(word1: String, word2: String) extends Affix
case class Suffix(word: String) extends Affix

object Markov {
  type Triplet = List[String]

  /**
   * Tokenizes tweet.
   *
   * Does not delimit on: alphanumerics, single quotes, #, &, [], (), <>, /
   */
  def tokenize(t: Tweet): List[String] = """[\w'’#\/&\[\]\(\)\<\>]+|[.,!?:;]""".r.findAllIn(t.text).toList

  /**
   * Using a tokenized sentence, uses a sliding window of 3 by steps of 1 to generate
   * word triplets.
   *
   * e.g. a sentence "i am a test sentence" becomes:
   *
   * List(
   *   List("i am a"),
   *   List("am a test"),
   *   List("a test sentence"))
   */
  def makeTriplets(words: List[String]): List[Triplet] = words.sliding(3, 1).toList

  /**
   * Generates a map keyed by prefixes (pairs of first two words of a triplet)
   * and values as the set of suffixes (single words) seen after prefixes.
   */
  def makeAffixMap(triplets: List[Triplet]): Map[Prefix, Set[Suffix]] = {
    triplets.foldLeft(Map[Prefix, Set[Suffix]]()) { (m, triplet) =>
      triplet match {
        case first :: second :: last :: Nil =>
          val prefix = Prefix(first, second)
          val suffix = Suffix(last)

          m.get(prefix) match {
            case Some(suffixes) => m + (prefix -> (suffixes + suffix))
            case None           => m + (prefix -> Set(suffix))
          }
        case first :: second :: Nil =>
          val prefix = Prefix(first, second)

          m.get(prefix) match {
            case Some(suffixes) => m + (prefix -> suffixes)
            case None           => m + (prefix -> Set())
          }
        case _ =>
          m
      }
    }
  }

  /**
   * Intersperses word collection with spaces and trims ends.
   */
  def wordsToSentence(words: Vector[String]): String = {
    val punctuation = Set(":", ";", ".", ",", "!", "?")

    words.foldLeft("") { (sentenceFragment, word) =>
      if (punctuation(word)) {
        sentenceFragment + word
      } else {
        sentenceFragment + " " + word
      }
    }.trim
  }

  /**
   * Checks if word collection, when interspersed with spaces, is of tweetable length.
   */
  def isValidTweetLength(words: Vector[String]): Boolean =
    wordsToSentence(words).length <= 140

  def randomFromIterable[T](iterable: Iterable[T]): T = {
    val vector = iterable.toVector
    vector((new scala.util.Random).nextInt(vector.length))
  }

  def walkChain(affixMap: Map[Prefix, Set[Suffix]]): Vector[String] = {
    def walkChain(acc: Vector[String]): Vector[String] = {
      val lastTwo = acc.takeRight(2)
      val prefix = Prefix(lastTwo(0), lastTwo(1))

      affixMap.get(prefix) match {
        case Some(suffixSet) if !suffixSet.isEmpty =>
          val wordToBeAppended = randomFromIterable(suffixSet).word
          val chain = acc :+ wordToBeAppended

          if (isValidTweetLength(chain)) {
            walkChain(chain)
          } else {
            acc
          }
        case _ =>
          acc
      }
    }

    // Choose a random prefix to start chain
    val start = randomFromIterable(affixMap.keys)

    walkChain(Vector(start.word1, start.word2))
  }
}
