package markovchainz

import org.scalactic.TypeCheckedTripleEquals

import org.scalatest.{ Matchers, WordSpec }
import org.scalatest.prop.GeneratorDrivenPropertyChecks

import scala.io.Source

class MarkovSpec extends WordSpec
  with Generators
  with Matchers
  with GeneratorDrivenPropertyChecks
  with TypeCheckedTripleEquals { self =>

  import Markov._

  "Markov.tokenize" should {
    "work properly for a simple example" in {
      val tweet = RawTweet("i am a simple example, tokenize me")
      val tokenized = tokenize(tweet)

      tokenized.length should === (8)
      tokenized should === (List("i", "am", "a", "simple", "example", ",", "tokenize", "me"))
    }

    "not delimit on certain special characters: ' ’ # / & [ ] ( ) < >" in {
      val tweet = RawTweet("#<h>e[l]l(o)'’/&")
      val tokenized = tokenize(tweet)

      tokenized.length should === (1)
    }
  }

  "Markov.makeTriplets" should {
    "work properly for a simple example" in {
      val sentence = List("i", "am", "a", "simple", "sentence")
      val triplets = makeTriplets(sentence)

      triplets.length should === (3)
      triplets should === (List(
        List("i", "am", "a"),
        List("am", "a", "simple"),
        List("a", "simple", "sentence")))
    }

    "be empty given an empty sentence" in {
      val sentence = List[String]()
      val triplets = makeTriplets(sentence)

      triplets shouldBe empty
    }
  }

  "Markov.makeAffixMap" should {
    "work properly for a simple example" in {
      val sentence = List("i", "am", "a", "simple", "sentence", "a", "simple", "one")
      val triplets = makeTriplets(sentence)

      val affixMap = makeAffixMap(triplets)

      affixMap should === (Map(
        Prefix("am", "a")            -> Set(Suffix("simple")),
        Prefix("a", "simple")        -> Set(Suffix("sentence"), Suffix("one")),
        Prefix("simple", "sentence") -> Set(Suffix("a")),
        Prefix("sentence", "a")      -> Set(Suffix("simple")),
        Prefix("i", "am")            -> Set(Suffix("a"))))
    }
  }

  "Markov.wordsToSentence" should {
    "work properly for simple examples" in {
      val words1 = Vector("i", "am", ";", "a", ":", "simple", "sentence", ":", "(", ",", "a", "simple", "one", ".", ":", ")")
      val words2 = Vector("hello", "world", "what's", "up")

      val sentence1 = wordsToSentence(words1)
      val sentence2 = wordsToSentence(words2)

      sentence1 should === ("i am; a: simple sentence :(, a simple one. :)")
      sentence2 should === ("hello world what's up")
    }

    "be a noop for empty words" in {
      wordsToSentence(Vector[String]()) shouldBe empty
    }
  }

  "Markov.isValidTweetLength" should {
    "be true for a simple examples <= 140 chars" in forAll(genStringOfLengthRange(0, 140)) { (s: String) =>
      val sentence = s.split(" ").toVector

      isValidTweetLength(sentence) shouldBe true
    }

    "be false for a simple examples > 140 chars" in forAll(genStringOfLengthRange(141, 500)) { (s: String) =>
      val sentence = s.split(" ").toVector

      isValidTweetLength(sentence) shouldBe false
    }
  }

  "Markov.walkChain" should {
    "generate valid tweets under 140 chars for a simple example" in {
      val affixMap = Map(
        Prefix("i", "am")            -> Set(Suffix("a")),
        Prefix("am", "a")            -> Set(Suffix("simple")),
        Prefix("a", "simple")        -> Set(Suffix("sentence"), Suffix("one")),
        Prefix("simple", "sentence") -> Set(Suffix("a")),
        Prefix("sentence", "a")      -> Set(Suffix("simple")))

      (0 to 100).foreach { _ =>
        val chain = wordsToSentence(walkChain(affixMap))

        chain should not be empty
        chain.length should be <= 140
      }
    }
  }
}
