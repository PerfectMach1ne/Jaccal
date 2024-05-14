ThisBuild / scalaVersion := "3.4.1"
ThisBuild / organization := "uni.jaccal"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / compileOrder := CompileOrder.Mixed

lazy val supportedScalaVersions = List("3.4.1")

lazy val root = (project in file("."))
  .aggregate(coreScala, legacyJava)
  .settings(
    name := "Jaccal",
    crossScalaVersions := Nil,
    publish / skip := false,

    // Has to be in the aggregating root project, otherwise Main.scala SWing imports are not resolved correctly.
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",

    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-encoding",
      "utf8",
      "-feature"
    ),

    // sbt assembly to compile an ... über-JAR.
    // https://github.com/sbt/sbt-assembly
    assembly / mainClass := Some("main.scala.Main")
  )

lazy val legacyJava = (project in file ("./main/java"))
  .settings(
    crossScalaVersions := supportedScalaVersions,
    crossPaths := false,
    autoScalaLibrary := false,
  )

lazy val coreScala = (project in file ("./main/scala"))
  .dependsOn(legacyJava)
  .settings(
    crossScalaVersions := supportedScalaVersions,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
  )