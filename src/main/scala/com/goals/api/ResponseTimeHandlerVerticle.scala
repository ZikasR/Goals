package com.goals.api

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.ResponseTimeHandler

import scala.concurrent.Future

class ResponseTimeHandlerVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)


    router
      .route("/helloWorld/")
      .handler(ResponseTimeHandler.create())
      .handler(_.response()
        .setChunked(true)
        .write("Hello Word, check the x-response-time header")
        end()
      )

    router
      .route("/hello/")
      .handler(_.response()
        .setChunked(true)
        .write("Hello, the x-response-time header shouldn't be in the response")
        end()
      )

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8686, "0.0.0.0")
  }
}
