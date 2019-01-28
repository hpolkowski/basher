name := "basher"

version := "0.1"

scalaVersion := "2.12.8"

mainClass in (Compile, run) := Some("Application")

resolvers ++= Seq(
  "Millhouse Bintray"  at "http://dl.bintray.com/themillhousegroup/maven"
)

libraryDependencies ++= Seq(
  
  // Obsługa Jsona
  "com.typesafe.play" %% "play-json" % "2.6.10",

  // Odczytywanie HTML
  // https://github.com/themillhousegroup/scoup
  "com.themillhousegroup" %% "scoup" % "0.4.6"
)
