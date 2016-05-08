package markovchainz

import java.io.File

import org.scalactic.TypeCheckedTripleEquals

import org.scalatest.{ Matchers, WordSpec }

import scala.io.Source

class TweetParserSpec extends WordSpec
  with Matchers
  with TypeCheckedTripleEquals {

  import TweetParser._

  private val testTweetsFile = new File(getClass.getResource("/test-tweets.csv").toURI)

  "TweetParser.allTweetsFromCsv" should {
    // total lines minus header row
    val linesCount = Source.fromFile(testTweetsFile).getLines.length - 1

    "contain all parsed tweets" in {
      val tweets = allTweetsFromCsv(testTweetsFile)

      tweets.length should === (linesCount)
    }
  }

  "TweetParser.cleanedTweetsFromCsv" should {
    "contain the correct number of parsed tweets after filtering" in {
      val tweets = cleanedTweetsFromCsv(testTweetsFile)
      val magicNumOfValidTweets = 15

      tweets.length should === (magicNumOfValidTweets)
    }
  }

  "TweetParser.stripHandles" should {
    "strip @-mentions out for a simple example" in {
      val s = "hello @oacgnol i am a tweet @hello @_test"

      stripHandles(s) should === ("hello  i am a tweet  ")
    }

    "be a noop for an example without any '@' symbols" in {
      val s = "hello i am a tweet hello test"

      stripHandles(s) should === (s)
    }

    "leave '@'s alone when not a proper @-mention" in {
      val s = "this is an @-mention, where the @-symbol isn't a mention"

      stripHandles(s) should === (s)
    }
  }

  "TweetParser.stripUrls" should {
    "strip urls out for a simple example" in {
      val s = "hello http://www.test.com i am a tweet https://t.co/blah"

      stripUrls(s) should === ("hello  i am a tweet ")
    }

    "be a noop for an example without any urls" in {
      val s = "hello i am a tweet hello test"

      stripUrls(s) should === (s)
    }
  }

  "TweetParser.replaceHtmlChars" should {
    "replace html chars with correct string representations for a simple example" in {
      val s = "me &amp; &lt;you&gt;"

      replaceHtmlChars(s) should === ("me & <you>")
    }

    "be a noop for an example without any html chars" in {
      val s = "hello i am a tweet hello test"

      replaceHtmlChars(s) should === (s)
    }
  }

  "TweetParser.stripExtraWhitespace" should {
    "strip extra whitespace out for a simple example" in {
      val s = "hello      this is a test tweet   "

      stripExtraWhitespace(s) should === ("hello this is a test tweet")
    }

    "be a noop for an example without any extra whitespace" in {
      val s = "hello i am a tweet"

      stripExtraWhitespace(s) should === (s)
    }
  }

  "TweetParser.isRetweet" should {
    "be true if tweet text starts with RT" in {
      val tweet = RawTweet("RT @test test tweet")

      isRetweet(tweet) shouldBe true
    }

    "be false if tweet text does not starts with RT" in {
      val tweet = RawTweet("@test test tweet")

      isRetweet(tweet) shouldBe false
    }
  }
}
