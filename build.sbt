ThisBuild / version := "0.1.0-SNAPSHOT"

val openRtbCoreVersion = "1.5.5"
val googleCommonProtosVersion = "1.12.0"

val commonSettings: Seq[Def.Setting[_]] = Seq[Def.Setting[_]](
  scalaVersion := "2.12.14",
  organization := "com.td",
)

def scalaGrpcSettings: Seq[Def.Setting[_]] = Seq[Def.Setting[_]](
  libraryDependencies += "com.thesamet.scalapb"  %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  libraryDependencies += "com.google.api.grpc" % "proto-google-common-protos" % googleCommonProtosVersion % "protobuf",

  PB.targets in Compile := Seq(
    PB.gens.java -> (sourceManaged in Compile).value,
    scalapb.gen(javaConversions = true) -> (sourceManaged in Compile).value
  ),

  PB.includePaths in Compile := Seq(
    target.value / "protobuf_external",
),
  PB.protoSources in Compile := Seq(
    PB.externalIncludePath.value / "google" / "api",
    target.value / "protobuf_external",
    new File("definitions/common")
  ),

  PB.additionalDependencies := Nil
)

lazy val root = (project in file("."))
  .settings(
    name := "proto-path-error"
  ).settings(scalaGrpcSettings)