package com.goals.api

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router

import scala.concurrent.Future

class ErrorHandlingVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    router
      .get("/somepath/path1/")
      .handler(_ => {
        throw new RuntimeException("something happened!")
      })

    router
      .get("/somepath/path2")
        .handler(_.fail(403))

    router
        .get("/somepath/*")
        .failureHandler(frc => {
          val statusCode = frc.statusCode()
          println(s"status code: $statusCode")

          frc
            .response
            .setStatusCode(statusCode)
            .end("Sorry! Not today")
        })

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8888, "0.0.0.0")
  }
}
