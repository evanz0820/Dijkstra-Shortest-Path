name := "DijkstraGraphX"
version := "0.1"
scalaVersion := "2.12.17"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.5",
  "org.apache.spark" %% "spark-graphx" % "3.5.5"
)
