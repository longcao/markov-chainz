package markovchainz

import java.io.InputStream

import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._

object TweetParser {
  def stripHandles(s: String): String = """\@[A-Za-z0-9_]+""".r.replaceAllIn(s, "")

  def stripUrls(s: String): String = """http[s]?:\/\/\S+""".r.replaceAllIn(s, "")

  def replaceHtmlChars(s: String): String =
    s.replaceAll("&amp;", "&")
      .replaceAll("&gt;", ">")
      .replaceAll("&lt;", "<")

  def stripExtraWhitespace(s: String): String = s.replaceAll("\\s+", " ").trim

  def isRetweet(text: String): Boolean = text.startsWith("RT")

  def allTweetsFromCsv(is: InputStream): Vector[Tweet] = {
    is.asCsvReader[Tweet](rfc.withHeader).collect {
      case Right(tweet) => tweet
    }.toVector
  }

  def cleanedTweetsFromCsv(is: InputStream): Vector[Tweet] = {
    val cleanerFn = stripHandles _       andThen
                    stripUrls _          andThen
                    replaceHtmlChars _   andThen
                    stripExtraWhitespace

    allTweetsFromCsv(is)
      .filterNot(tweet => isRetweet(tweet.text))
      .map(tweet => tweet.copy(text = cleanerFn(tweet.text)))
  }
}
