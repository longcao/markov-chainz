package markovchainz

import java.io.File

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

  def allTweetsFromCsv(file: File): Vector[Tweet] = {
    file.asCsvReader[Tweet](rfc.withHeader).collect {
      case Success(tweet) => tweet
    }.toVector
  }

  def cleanedTweetsFromCsv(file: File): Vector[Tweet] = {
    val cleanerFn = stripHandles _       andThen
                    stripUrls _          andThen
                    replaceHtmlChars _   andThen
                    stripExtraWhitespace

    allTweetsFromCsv(file)
      .filterNot(tweet => isRetweet(tweet.text))
      .map(tweet => tweet.copy(text = cleanerFn(tweet.text)))
  }
}
