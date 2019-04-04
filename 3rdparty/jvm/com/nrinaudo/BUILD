load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
scala_import(
    name = "kantan_codecs",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/spire_math:imp"
    ],
    jars = [
        "//external:jar/com/nrinaudo/kantan_codecs_2_12"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "kantan_codecs_shapeless",
    exports = [
        "//3rdparty/jvm/com/chuusai:shapeless",
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":kantan_codecs"
    ],
    jars = [
        "//external:jar/com/nrinaudo/kantan_codecs_shapeless_2_12"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "kantan_csv",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":kantan_codecs"
    ],
    jars = [
        "//external:jar/com/nrinaudo/kantan_csv_2_12"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "kantan_csv_generic",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":kantan_codecs_shapeless",
        ":kantan_csv"
    ],
    jars = [
        "//external:jar/com/nrinaudo/kantan_csv_generic_2_12"
    ],
    visibility = [
        "//visibility:public"
    ]
)


