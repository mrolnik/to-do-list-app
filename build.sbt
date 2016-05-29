name := """to-do-list-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  "com.typesafe.play" %% "anorm" % "2.5.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
  specs2
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
