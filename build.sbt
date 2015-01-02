name := """oka"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

val appDependencies = Seq(
  jdbc,
  anorm,
  "org.postgresql" % "postgresql" % "9.3-1102.jdbc41"
)

libraryDependencies ++= Seq(
)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.1"
)

