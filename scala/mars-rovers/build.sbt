name := "mars-rovers"

version := "0.1"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-deprecation", "-unchecked")

resolvers ++= Seq("Specs2 releases" at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.8.2" % "test")

