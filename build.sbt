ThisBuild / organization := "com.goals"

lazy val goals = (project in file("."))
    .settings(
        name := "Goals",
        libraryDependencies ++= Vector (
            "io.vertx" %% "vertx-web-scala" % "3.5.4",
        )
    )