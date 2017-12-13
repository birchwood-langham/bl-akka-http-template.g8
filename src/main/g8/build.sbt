name := "$name;format="lower,hyphen"$"

lazy val commonSettings = Seq(
  organization := "com.birchwoodlangham",
  version := "0.0.1-SNAPSHOT",
  scalaVersion in ThisBuild := "2.12.4",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8"),
  javacOptions ++= Seq("-encoding", "UTF-8")
)

lazy val commonResolvers = Seq(
  "Maven Central Proxy" at "http://reaper.birchwoodlangham.com:8081/repository/maven-central/",
  "Sonatype Nexus Releases" at "http://reaper.birchwoodlangham.com:8081/repository/maven-releases/",
  "Sonatype Nexus Snapshots" at "http://reaper.birchwoodlangham.com:8081/repository/maven-snapshots/"
)

val nscalaTimeVersion = "2.16.0" // https://github.com/nscala-time/nscala-time
val scalaLoggingVersion = "3.7.2" // https://github.com/typesafehub/scala-logging
val logbackVersion = "1.2.3" // http://logback.qos.ch/
val configVersion = "1.3.1" // https://github.com/typesafehub/config
val scalaTestVersion = "3.0.4" // http://www.scalatest.org/
val scalacticVersion = "3.0.4" // http://www.scalatest.org/
val akkaHttpVersion = "10.0.11" // http://doc.akka.io/docs/akka-http/current/
val akkaVersion = "2.5.7" // http://akka.io/docs

lazy val commonDependencies = Seq(
  "com.github.nscala-time" %% "nscala-time" % nscalaTimeVersion,  // use nscala-time for time manipulation
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion, // use scala-logging and logback for logging
  "ch.qos.logback" % "logback-classic" % logbackVersion, // required by scala-logging
  "com.typesafe" % "config" % configVersion // use typesafe's configuration library for reading configuration files
)

lazy val testDependencies = Seq(
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "org.scalactic" %% "scalactic" % scalaTestVersion
)

lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)

lazy val root = project.in(file("."))
  .settings(commonSettings:_*)
  .settings(
    resolvers ++= commonResolvers,
    libraryDependencies ++= commonDependencies ++ testDependencies ++ akkaDependencies
  )

// the next statuement is required to workaround the issues with publishing to Sonatype Nexus with sbt 1.0.X+
// updateOptions := updateOptions.value.withGigahorse(false)

// this block is only required if you want to publish an artifact to Sonatype Nexus
//
// credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
//
// publishTo := {
//   val nexus = "http://reaper.birchwoodlangham.com:8081/"
//   if (isSnapshot.value)
//     Some("snapshots" at nexus + "repository/maven-snapshots")
//   else
//     Some("releases" at nexus + "repository/maven-releases")
// }
