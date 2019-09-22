scala_test(
    name = "markov-chainz-test",
    srcs = glob(["*.scala"]),
    resources = [
        "//:tweets.csv",
        "//src/test/resources:test-tweets.csv",
    ],
    deps = [
        "//src/main/scala/markovchainz:markov-chainz",
        "@third_party_jvm//3rdparty/jvm/org/scalacheck",
        "@third_party_jvm//3rdparty/jvm/org/typelevel:cats_core",
    ],
)
