package com.goals.api

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.BodyHandler

import scala.concurrent.Future

class BodyHandlingVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    router
      .route
      .handler(BodyHandler.create())

    router
        .post("/goals/")
        .handler(rc => {
          val body = rc.getBodyAsString()

          rc.response
            .setChunked(true)
            .setStatusCode(201)
            .write(s"created : $body")
            .end
        })

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8999, "0.0.0.0")
  }

}
