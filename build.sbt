ThisBuild / organization := "com.goals"

lazy val goals = (project in file("."))
  .settings(
    name := "Goals",
    libraryDependencies ++= Vector(
      "io.vertx" %% "vertx-web-scala" % "3.6.0",
      "io.vertx" %% "vertx-auth-shiro-scala" % "3.6.0",
      "org.slf4j" % "slf4j-simple" % "1.7.25",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.5",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.9.5",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.9.5",
    )
  )