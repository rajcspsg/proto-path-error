ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "proto-path-error"
  )
val openRtbCoreVersion = "1.5.5"
val googleCommonProtosVersion = "1.12.0"
val scalatest = "3.0.5"
val grpcJavaVersion = scalapb.compiler.Version.grpcJavaVersion

val commonSettings: Seq[Def.Setting[_]] = Seq[Def.Setting[_]](
  scalaVersion := "2.12.14",
  organization := "com.td",
  crossScalaVersions := Seq("2.12.14", "2.11.12")
)


def scalaGrpcSettings: Seq[Def.Setting[_]] = Seq[Def.Setting[_]](
  libraryDependencies += "com.thesamet.scalapb"  %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  libraryDependencies += "com.google.api.grpc" % "proto-google-common-protos" % googleCommonProtosVersion % "protobuf",
  libraryDependencies += "com.google.openrtb"  % "openrtb-core" % openRtbCoreVersion,
  libraryDependencies += "com.google.openrtb"  % "openrtb-core"   % openRtbCoreVersion % "protobuf",
  // logging
  libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.17.0",
  libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  // test
  libraryDependencies += "org.scalatest"      %% "scalatest"    % scalatest % Test,

  PB.targets in Compile := Seq(
    PB.gens.java -> (sourceManaged in Compile).value,
    scalapb.gen(javaConversions = true) -> (sourceManaged in Compile).value
  ),

  PB.includePaths in Compile := Seq(
    target.value / "protobuf_external",
new File("definitions/common"),
),
  PB.protoSources in Compile := Seq(
    PB.externalIncludePath.value / "google" / "api",
    new File("definitions/common"),
    target.value / "protobuf_external"
  ),

  excludeFilter in PB.generate := new SimpleFileFilter(file => file.getCanonicalPath.contains("google/protobuf")),

  PB.additionalDependencies := Nil
)