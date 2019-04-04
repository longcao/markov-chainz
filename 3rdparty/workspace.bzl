# Do not edit. bazel-deps autogenerates this file from dependencies.yaml.
def _jar_artifact_impl(ctx):
    jar_name = "%s.jar" % ctx.name
    ctx.download(
        output=ctx.path("jar/%s" % jar_name),
        url=ctx.attr.urls,
        sha256=ctx.attr.sha256,
        executable=False
    )
    src_name="%s-sources.jar" % ctx.name
    srcjar_attr=""
    has_sources = len(ctx.attr.src_urls) != 0
    if has_sources:
        ctx.download(
            output=ctx.path("jar/%s" % src_name),
            url=ctx.attr.src_urls,
            sha256=ctx.attr.src_sha256,
            executable=False
        )
        srcjar_attr ='\n    srcjar = ":%s",' % src_name

    build_file_contents = """
package(default_visibility = ['//visibility:public'])
java_import(
    name = 'jar',
    tags = ['maven_coordinates={artifact}'],
    jars = ['{jar_name}'],{srcjar_attr}
)
filegroup(
    name = 'file',
    srcs = [
        '{jar_name}',
        '{src_name}'
    ],
    visibility = ['//visibility:public']
)\n""".format(artifact = ctx.attr.artifact, jar_name = jar_name, src_name = src_name, srcjar_attr = srcjar_attr)
    ctx.file(ctx.path("jar/BUILD"), build_file_contents, False)
    return None

jar_artifact = repository_rule(
    attrs = {
        "artifact": attr.string(mandatory = True),
        "sha256": attr.string(mandatory = True),
        "urls": attr.string_list(mandatory = True),
        "src_sha256": attr.string(mandatory = False, default=""),
        "src_urls": attr.string_list(mandatory = False, default=[]),
    },
    implementation = _jar_artifact_impl
)

def jar_artifact_callback(hash):
    src_urls = []
    src_sha256 = ""
    source=hash.get("source", None)
    if source != None:
        src_urls = [source["url"]]
        src_sha256 = source["sha256"]
    jar_artifact(
        artifact = hash["artifact"],
        name = hash["name"],
        urls = [hash["url"]],
        sha256 = hash["sha256"],
        src_urls = src_urls,
        src_sha256 = src_sha256
    )
    native.bind(name = hash["bind"], actual = hash["actual"])


def list_dependencies():
    return [
    {"artifact": "com.chuusai:shapeless_2.12:2.3.3", "lang": "scala", "sha1": "6041e2c4871650c556a9c6842e43c04ed462b11f", "sha256": "312e301432375132ab49592bd8d22b9cd42a338a6300c6157fb4eafd1e3d5033", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/chuusai/shapeless_2.12/2.3.3/shapeless_2.12-2.3.3.jar", "source": {"sha1": "02511271188a92962fcf31a9a217b8122f75453a", "sha256": "2d53fea1b1ab224a4a731d99245747a640deaa6ef3912c253666aa61287f3d63", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/chuusai/shapeless_2.12/2.3.3/shapeless_2.12-2.3.3-sources.jar"} , "name": "com_chuusai_shapeless_2_12", "actual": "@com_chuusai_shapeless_2_12//jar:file", "bind": "jar/com/chuusai/shapeless_2_12"},
    {"artifact": "com.nrinaudo:kantan.codecs-shapeless_2.12:0.5.0", "lang": "scala", "sha1": "460dbdf838a7ceb04599d2706e7d2e35e1698fe5", "sha256": "4d8ade56835fc23d7912f3eb6d2945c993727ccaa10048a0403fa013bc1bb05e", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.codecs-shapeless_2.12/0.5.0/kantan.codecs-shapeless_2.12-0.5.0.jar", "source": {"sha1": "587b833887e7ca5d7e3526a9142bfd77b5f59700", "sha256": "5ec95499c5b248a26f05d6b5a8224d72cc999387055bbce9c8d4730a3bcc39ed", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.codecs-shapeless_2.12/0.5.0/kantan.codecs-shapeless_2.12-0.5.0-sources.jar"} , "name": "com_nrinaudo_kantan_codecs_shapeless_2_12", "actual": "@com_nrinaudo_kantan_codecs_shapeless_2_12//jar:file", "bind": "jar/com/nrinaudo/kantan_codecs_shapeless_2_12"},
    {"artifact": "com.nrinaudo:kantan.codecs_2.12:0.5.0", "lang": "scala", "sha1": "8b3450538b19286fb5dc80321361261e18fb0126", "sha256": "b877a57b1c15c044f76353b8b912ab252c2c451a43dfeacdc0782766aaa32704", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.codecs_2.12/0.5.0/kantan.codecs_2.12-0.5.0.jar", "source": {"sha1": "1802acdeabf9047fbb3a2c6adfe18981e4bf786f", "sha256": "b2b71897dbe2d5de92aa0554b42d21f5c890973474c01bebb87d954121ecdf4b", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.codecs_2.12/0.5.0/kantan.codecs_2.12-0.5.0-sources.jar"} , "name": "com_nrinaudo_kantan_codecs_2_12", "actual": "@com_nrinaudo_kantan_codecs_2_12//jar:file", "bind": "jar/com/nrinaudo/kantan_codecs_2_12"},
    {"artifact": "com.nrinaudo:kantan.csv-generic_2.12:0.5.0", "lang": "scala", "sha1": "a3aec22b9bd55dd5f47a47d36b32a8c48bc75b1c", "sha256": "0d5780d8031fb61ef223cebe87a0c570c2a339b70d83ee191376e2fa7955c885", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.csv-generic_2.12/0.5.0/kantan.csv-generic_2.12-0.5.0.jar", "source": {"sha1": "0796ee3159fc14fd15c0ddce3a5fd70a073f9c47", "sha256": "7aac76ee47243e20f54e9fda9daeacbc912aaf1c5027e5dc3d88a30f7b7bbcd7", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.csv-generic_2.12/0.5.0/kantan.csv-generic_2.12-0.5.0-sources.jar"} , "name": "com_nrinaudo_kantan_csv_generic_2_12", "actual": "@com_nrinaudo_kantan_csv_generic_2_12//jar:file", "bind": "jar/com/nrinaudo/kantan_csv_generic_2_12"},
    {"artifact": "com.nrinaudo:kantan.csv_2.12:0.5.0", "lang": "scala", "sha1": "076c2e2ba79a6ef78cedfeb2ad35aea69bafce44", "sha256": "c40f7ab483def635bd457956f27e2e481e79351c0887f1d6bad773c96e6a58f2", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.csv_2.12/0.5.0/kantan.csv_2.12-0.5.0.jar", "source": {"sha1": "7628bf0ed668f7fd29ccad9c8d7de77384ce3d84", "sha256": "355ca16260193e31ad47d59b8fb810bead2beae140f80e7b1b4aca46285b5f74", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/nrinaudo/kantan.csv_2.12/0.5.0/kantan.csv_2.12-0.5.0-sources.jar"} , "name": "com_nrinaudo_kantan_csv_2_12", "actual": "@com_nrinaudo_kantan_csv_2_12//jar:file", "bind": "jar/com/nrinaudo/kantan_csv_2_12"},
    {"artifact": "org.scala-sbt:test-interface:1.0", "lang": "java", "sha1": "0a3f14d010c4cb32071f863d97291df31603b521", "sha256": "15f70b38bb95f3002fec9aea54030f19bb4ecfbad64c67424b5e5fea09cd749e", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scala-sbt/test-interface/1.0/test-interface-1.0.jar", "source": {"sha1": "d44b23e9e3419ad0e00b91bba764a48d43075000", "sha256": "c314491c9df4f0bd9dd125ef1d51228d70bd466ee57848df1cd1b96aea18a5ad", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scala-sbt/test-interface/1.0/test-interface-1.0-sources.jar"} , "name": "org_scala_sbt_test_interface", "actual": "@org_scala_sbt_test_interface//jar", "bind": "jar/org/scala_sbt/test_interface"},
    {"artifact": "org.scalacheck:scalacheck_2.12:1.13.5", "lang": "scala", "sha1": "fff972ccaa0d83ca53bfdbbd0763c1059281fb76", "sha256": "044b587dea797a58ca216563e5c9b86d2728e4ad8796355e0dc628203feb8ed4", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scalacheck/scalacheck_2.12/1.13.5/scalacheck_2.12-1.13.5.jar", "source": {"sha1": "d0e1376902de65c7fa327f455b61d5d71957d78e", "sha256": "459c564b334139a8e1e229c74162e78314dc6d91999d8fcbd1d76132311e8df8", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scalacheck/scalacheck_2.12/1.13.5/scalacheck_2.12-1.13.5-sources.jar"} , "name": "org_scalacheck_scalacheck_2_12", "actual": "@org_scalacheck_scalacheck_2_12//jar:file", "bind": "jar/org/scalacheck/scalacheck_2_12"},
    {"artifact": "org.scalactic:scalactic_2.12:3.0.5", "lang": "scala", "sha1": "edec43902cdc7c753001501e0d8c2de78394fb03", "sha256": "57e25b4fd969b1758fe042595112c874dfea99dca5cc48eebe07ac38772a0c41", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scalactic/scalactic_2.12/3.0.5/scalactic_2.12-3.0.5.jar", "source": {"sha1": "e02d37e95ba74c95aa9063b9114db51f2810b212", "sha256": "0455eaecaa2b8ce0be537120c2ccd407c4606cbe53e63cb6a7fc8b31b5b65461", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scalactic/scalactic_2.12/3.0.5/scalactic_2.12-3.0.5-sources.jar"} , "name": "org_scalactic_scalactic_2_12", "actual": "@org_scalactic_scalactic_2_12//jar:file", "bind": "jar/org/scalactic/scalactic_2_12"},
    {"artifact": "org.scalatest:scalatest_2.12:3.0.5", "lang": "scala", "sha1": "7bb56c0f7a3c60c465e36c6b8022a95b883d7434", "sha256": "b416b5bcef6720da469a8d8a5726e457fc2d1cd5d316e1bc283aa75a2ae005e5", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scalatest/scalatest_2.12/3.0.5/scalatest_2.12-3.0.5.jar", "source": {"sha1": "ec414035204524d3d4205ef572075e34a2078c78", "sha256": "22081ee83810098adc9af4d84d05dd5891d7c0e15f9095bcdaf4ac7a228b92df", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/scalatest/scalatest_2.12/3.0.5/scalatest_2.12-3.0.5-sources.jar"} , "name": "org_scalatest_scalatest_2_12", "actual": "@org_scalatest_scalatest_2_12//jar:file", "bind": "jar/org/scalatest/scalatest_2_12"},
    {"artifact": "org.spire-math:imp_2.12:0.3.0", "lang": "scala", "sha1": "1e719fee0789e742222d0c15270845c379199816", "sha256": "f85694baf776969115db81da72da38e1c3f4412e249f79c8db7e1a8a5f26b905", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/spire-math/imp_2.12/0.3.0/imp_2.12-0.3.0.jar", "source": {"sha1": "f42f38e37f6cbe5558ea06b9a5ed57d97cc604f3", "sha256": "f6369807276ae3645724f177241f642f1d2a469f8c3817cc60d647479e226abc", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/spire-math/imp_2.12/0.3.0/imp_2.12-0.3.0-sources.jar"} , "name": "org_spire_math_imp_2_12", "actual": "@org_spire_math_imp_2_12//jar:file", "bind": "jar/org/spire_math/imp_2_12"},
    {"artifact": "org.twitter4j:twitter4j-core:4.0.6", "lang": "java", "sha1": "f3722af4568b96ee66739267e13211b8b66ac7d4", "sha256": "fea0831b7237afcd5208ab8ae8d80f2e0a54a15b27f4b49bd63f0f3b63820841", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/twitter4j/twitter4j-core/4.0.6/twitter4j-core-4.0.6.jar", "source": {"sha1": "5a3a910793c3510a3e25cea3c51672bb88124527", "sha256": "6a5949b4f78a6d8e493d4ecd09fa7c253a89a28162e20ca07be3ae866aec423a", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/twitter4j/twitter4j-core/4.0.6/twitter4j-core-4.0.6-sources.jar"} , "name": "org_twitter4j_twitter4j_core", "actual": "@org_twitter4j_twitter4j_core//jar", "bind": "jar/org/twitter4j/twitter4j_core"},
    {"artifact": "org.typelevel:cats-core_2.12:1.6.0", "lang": "scala", "sha1": "803a6a316f72448a335fb023bf6b6b9338ff31f5", "sha256": "659c8df533e73dcc3d728bca8646e670ec8db376ba9679610fba88f3cf91105e", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/cats-core_2.12/1.6.0/cats-core_2.12-1.6.0.jar", "source": {"sha1": "0cab57e7586d2cdd5b2c8401551ce7f4b83162f5", "sha256": "181b6d67aaeb0df735a633dca48ccc7b7cdb6d9dd4c6a1e570610d83028bcc5a", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/cats-core_2.12/1.6.0/cats-core_2.12-1.6.0-sources.jar"} , "name": "org_typelevel_cats_core_2_12", "actual": "@org_typelevel_cats_core_2_12//jar:file", "bind": "jar/org/typelevel/cats_core_2_12"},
    {"artifact": "org.typelevel:cats-kernel_2.12:1.6.0", "lang": "scala", "sha1": "53269c4e5a11104572e8206d5e3066205f47b683", "sha256": "65250bc92ea5d49cb5ca55f3869cfc8e5f90689cc983bfaff2c9b7db165f3013", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/cats-kernel_2.12/1.6.0/cats-kernel_2.12-1.6.0.jar", "source": {"sha1": "93c3f9ea2a2016f4a343f13e2d6411e4b14b7e75", "sha256": "de715ed3405f9b5ada2bc10226fe586f0cf700d6951f665e4f1d9e6465e148d5", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/cats-kernel_2.12/1.6.0/cats-kernel_2.12-1.6.0-sources.jar"} , "name": "org_typelevel_cats_kernel_2_12", "actual": "@org_typelevel_cats_kernel_2_12//jar:file", "bind": "jar/org/typelevel/cats_kernel_2_12"},
    {"artifact": "org.typelevel:cats-macros_2.12:1.6.0", "lang": "scala", "sha1": "f8749de0b99036a9be74bd86ee5da4b26b097253", "sha256": "b9bb2c24f6af1279b051c0fd257e0420d2b407bb0d50febfee52a4b6d1a09aa0", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/cats-macros_2.12/1.6.0/cats-macros_2.12-1.6.0.jar", "source": {"sha1": "a97272b3b735c40d78dc8f0674612e09ec1df959", "sha256": "ee31f741462ae7e5a9df307da8abc80e99a76a112aea3cb7f67ff7dfa0f7d0ee", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/cats-macros_2.12/1.6.0/cats-macros_2.12-1.6.0-sources.jar"} , "name": "org_typelevel_cats_macros_2_12", "actual": "@org_typelevel_cats_macros_2_12//jar:file", "bind": "jar/org/typelevel/cats_macros_2_12"},
    {"artifact": "org.typelevel:machinist_2.12:0.6.6", "lang": "scala", "sha1": "4086874ad28be846916347dd74ba5395c63eaf50", "sha256": "e1f56da37a817ff9b20e36870ac39b64277155e82cd2cde25d76d10af14b8a96", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/machinist_2.12/0.6.6/machinist_2.12-0.6.6.jar", "source": {"sha1": "1aafa2c22a3fb1e2c1385a1c2b796340a166ff0b", "sha256": "b8b693aa0a844dd095550e9a2ce63870d605fdcc16447261ec063e80e0b4795c", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/machinist_2.12/0.6.6/machinist_2.12-0.6.6-sources.jar"} , "name": "org_typelevel_machinist_2_12", "actual": "@org_typelevel_machinist_2_12//jar:file", "bind": "jar/org/typelevel/machinist_2_12"},
    {"artifact": "org.typelevel:macro-compat_2.12:1.1.1", "lang": "scala", "sha1": "ed809d26ef4237d7c079ae6cf7ebd0dfa7986adf", "sha256": "8b1514ec99ac9c7eded284367b6c9f8f17a097198a44e6f24488706d66bbd2b8", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/macro-compat_2.12/1.1.1/macro-compat_2.12-1.1.1.jar", "source": {"sha1": "ade6d6ec81975cf514b0f9e2061614f2799cfe97", "sha256": "c748cbcda2e8828dd25e788617a4c559abf92960ef0f92f9f5d3ea67774c34c8", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/typelevel/macro-compat_2.12/1.1.1/macro-compat_2.12-1.1.1-sources.jar"} , "name": "org_typelevel_macro_compat_2_12", "actual": "@org_typelevel_macro_compat_2_12//jar:file", "bind": "jar/org/typelevel/macro_compat_2_12"},
    ]

def maven_dependencies(callback = jar_artifact_callback):
    for hash in list_dependencies():
        callback(hash)
