scala_test(
    name = "markov-chainz-test",
    srcs = glob(["*.scala"]),
    resources = [
        "//:tweets.csv",
        "//src/test/resources:test-tweets.csv",
    ],
    deps = [
        "//3rdparty/jvm/org/scalacheck",
        "//3rdparty/jvm/org/typelevel:cats_core",
        "//src/main/scala/markovchainz:markov-chainz",
    ],
)
