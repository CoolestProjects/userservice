name := """userservice"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.scalatestplus" % "play_2.11" % "1.2.0" % "test",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

sbt.Keys.fork in Test := false

