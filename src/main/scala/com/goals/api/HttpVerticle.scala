package com.goals.api

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.concurrent.Future

class HttpVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    router
      .route()
      .handler(routingContext => {
        routingContext
          .response()
          .setChunked(true)
          .putHeader("content-type", "text/plain")
          .write("default handler\n")
        routingContext.next()
      })
      .blockingHandler((routingContext: RoutingContext) => {
        routingContext.response.write("blocking handler\n")
        routingContext.next()
      }, false)

    router
      .get("/hello")
      .handler(_.response()
        .end("world")
      )

    router
      .route("/some/path")
      .handler(_.response()
        .end("this is a response to some path")
      )

    router
      .post("/goals/:description/")
      .handler(routingContext => {
        val description = routingContext.request.getParam("description")
        routingContext
          .response
          .end(s"this is the description of your Goal: ${description.get}")
      })

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8666, "0.0.0.0")
  }
}
