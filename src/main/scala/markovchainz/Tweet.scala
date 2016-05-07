package markovchainz

sealed abstract class Tweet {
  def text: String
}
case class RawTweet(text: String) extends Tweet
case class CleanedTweet(text: String) extends Tweet
