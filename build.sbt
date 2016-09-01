name := "ant-salesforce-ci"

version := "1.36.4"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.apache.ant" % "ant" % "1.8.3" % "compile",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5"
)

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

// Read here for optional dependencies: 
// http://etorreborre.github.com/specs2/guide/org.specs2.guide.Runners.html#Dependencies

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

scalacOptions in Test ++= Seq("-Yrangepos")
scalacOptions += "-feature"
