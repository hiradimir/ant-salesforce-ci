name := "ant-salesforce-ci"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.8.2" % "test",
  "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test",
  "junit" % "junit" % "4.9" % "test",
  "org.apache.ant" % "ant" % "1.8.3" % "compile"
)

// Read here for optional dependencies: 
// http://etorreborre.github.com/specs2/guide/org.specs2.guide.Runners.html#Dependencies

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
)


