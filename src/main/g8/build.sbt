lazy val scalaV = "2.12.3"

lazy val $server$ = (project in file("$server$")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := Seq($client$),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile <<= (compile in Compile) dependsOn scalaJSPipeline,
  libraryDependencies ++= Seq(
    "com.h2database" % "h2" % "1.4.196",
    "com.typesafe.play" %% "play-slick" % "3.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
    "com.vmunier" %% "scalajs-scripts" % "1.1.1",
    "com.lihaoyi" %% "autowire" % "0.2.6",
    guice,
    filters,
    specs2 % Test
  ),
  // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
  EclipseKeys.preTasks := Seq(compile in Compile)
).enablePlugins(PlayScala).
  dependsOn($shared$Jvm)

lazy val $client$ = (project in file("$client$")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  scalacOptions ++= Seq("-Xmax-classfile-name","78"),
  persistLauncher in Test := false,
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",
    "com.thoughtworks.binding" %%% "dom" % "11.0.0-M3",
    "com.thoughtworks.binding" %%% "futurebinding" % "11.0.0-M3",
    "com.lihaoyi" %%% "autowire" % "0.2.6",
    "fr.hmil" %%% "roshttp" % "2.0.2"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn($shared$Js)

lazy val $shared$ = (crossProject.crossType(CrossType.Pure) in file("$shared$")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val $shared$Jvm = $shared$.jvm
lazy val $shared$Js = $shared$.js

// loads the $server$ project at sbt startup
onLoad in Global := (Command.process("project $server$", _: State)) compose (onLoad in Global).value
