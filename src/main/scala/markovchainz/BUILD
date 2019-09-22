scala_binary(
    name = "markov-chainz",
    srcs = glob(["*.scala"]),
    main_class = "markovchainz.Main",
    resources = [
        "//:tweets.csv",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "@third_party_jvm//3rdparty/jvm/com/nrinaudo:kantan_csv_generic",
        "@third_party_jvm//3rdparty/jvm/org/twitter4j:twitter4j_core",
        "@third_party_jvm//3rdparty/jvm/org/typelevel:cats_core",
    ],
)
