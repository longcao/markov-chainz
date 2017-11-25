package markovchainz

case class Tweet(
  tweet_id                    : Long,
  in_reply_to_status_id       : Option[Long],
  in_reply_to_user_id         : Option[Long],
  timestamp                   : String,
  source                      : String,
  text                        : String,
  retweeted_status_id         : Option[Long],
  retweeted_status_user_id    : Option[Long],
  retweeted_status_timestamp  : Option[String],
  expanded_urls               : Option[String])
