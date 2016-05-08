package markovchainz

import java.io.File

import net.tixxit.delimited._

object TweetParser {
  def stripHandles(s: String): String = """\@[A-Za-z0-9_]+""".r.replaceAllIn(s, "")

  def stripUrls(s: String): String = """http[s]?:\/\/\S+""".r.replaceAllIn(s, "")

  def replaceAmpersands(s: String): String = s.replaceAll("&amp;", "&")

  def stripExtraWhitespace(s: String): String = s.replaceAll("\\s+", " ").trim

  def isRetweet(tweet: Tweet): Boolean = tweet.text.startsWith("RT")

  def allTweetsFromCsv(file: File): Vector[RawTweet] = {
    val parser: DelimitedParser = DelimitedParser(DelimitedFormat.CSV)

    val rows: Vector[Either[DelimitedError, Row]] =
      parser.parseFile(file)

    // ignore header row and only collect succesfully parsed rows
    rows.tail.collect {
      case Right(row) => RawTweet(text = row(5))
    }
  }

  def cleanedTweetsFromCsv(file: File): Vector[CleanedTweet] = {
    val cleanerFn = stripHandles _       andThen
                    stripUrls _          andThen
                    replaceAmpersands _  andThen
                    stripExtraWhitespace

    allTweetsFromCsv(file)
      .filterNot(isRetweet)
      .map(tweet => CleanedTweet(cleanerFn(tweet.text)))
  }
}
