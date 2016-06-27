name := "ant-salesforce-ci"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.4" % "test",
  "org.apache.ant" % "ant" % "1.8.3" % "compile",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5"
)

// Read here for optional dependencies: 
// http://etorreborre.github.com/specs2/guide/org.specs2.guide.Runners.html#Dependencies

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

scalacOptions in Test ++= Seq("-Yrangepos")
