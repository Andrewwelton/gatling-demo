import Dependencies._

enablePlugins(GatlingPlugin)

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "Magnet Forensics",
      scalaVersion := "2.12.7",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "Gatling Demo",
    libraryDependencies ++= gatling
  )
