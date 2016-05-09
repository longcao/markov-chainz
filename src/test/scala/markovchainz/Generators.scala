package markovchainz

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen

trait Generators {
  def genStringOfLengthRange(min: Int, max: Int): Gen[String] = {
    for {
      n <- Gen.choose(min, max) // choose is *inclusive*
      chars <- Gen.listOfN(n, arbitrary[Char])
      str = chars.mkString
    } yield str
  }
}

object Generators extends Generators
