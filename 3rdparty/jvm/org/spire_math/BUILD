load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
scala_import(
    name = "imp",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library"
    ],
    jars = [
        "//external:jar/org/spire_math/imp_2_12"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)


