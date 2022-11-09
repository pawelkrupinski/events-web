name := "events"
organization := "net.pawel"
version := "1.0"

scalaVersion := "2.13.10"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"
libraryDependencies += "com.konghq" % "unirest-java" % "3.13.11"
libraryDependencies += "org.jsoup" % "jsoup" % "1.15.3"
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.3"