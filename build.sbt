import _root_.sbt.Keys._

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
  "net.logstash.logback" % "logstash-logback-encoder" % "2.7",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.scalatestplus" % "play_2.11" % "1.2.0" % "test",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "com.github.mumoshu" %% "play2-memcached" % "0.6.0",
  "org.antlr" % "stringtemplate" % "4.0.2"
)

resolvers += "Spy Repository" at "http://files.couchbase.com/maven2" // required to resolve `spymemcached`, the plugin's dependency.

sbt.Keys.fork in Test := false

