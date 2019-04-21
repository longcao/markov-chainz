scala_binary(
    name = "markov-chainz",
    srcs = glob(["*.scala"]),
    main_class = "markovchainz.Main",
    deps = [
        "//3rdparty/jvm/com/nrinaudo:kantan_csv_generic",
        "//3rdparty/jvm/org/twitter4j:twitter4j_core",
        "//3rdparty/jvm/org/typelevel:cats_core"
    ],
    visibility = ["//visibility:public"]
)
