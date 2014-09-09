import org.sbtidea.SbtIdeaPlugin._
import sbtassembly.Plugin.AssemblyKeys._

name := "tk-projekt"

version := "1.0.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.3.12" % "test"
)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

antlr4Settings

antlr4GenListener in Antlr4 := false

antlr4GenVisitor in Antlr4 := true

antlr4PackageName in Antlr4 := Some("parser")

antlr4OutputDir in Antlr4 := (javaSource in Compile).value

assemblySettings

test in assembly := {}

jarName in assembly := "simplifier.jar"

mainClass in assembly := Some("runner.Runner")

ideaExcludeFolders ++= Seq(".idea_modules", ".idea", "target")
