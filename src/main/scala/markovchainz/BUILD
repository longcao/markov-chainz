scala_library(
    name = "markov-chainz-lib",
    srcs = [
        "Markov.scala",
        "Markovchainz.scala",
        "Tweet.scala",
        "TweetParser.scala",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "@third_party_jvm//3rdparty/jvm/com/nrinaudo:kantan_csv_generic",
        "@third_party_jvm//3rdparty/jvm/org/typelevel:cats_core",
    ],
)

scala_binary(
    name = "markov-chainz",
    srcs = ["Main.scala"],
    main_class = "markovchainz.Main",
    resources = [
        "//:tweets.csv",
    ],
    visibility = ["//visibility:public"],
    deps = [
        ":markov-chainz-lib",
        "@third_party_jvm//3rdparty/jvm/org/twitter4j:twitter4j_core",
    ],
)
