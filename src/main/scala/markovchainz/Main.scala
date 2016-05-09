package markovchainz

import java.io.File

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
