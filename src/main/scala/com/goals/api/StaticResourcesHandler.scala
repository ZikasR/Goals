package com.goals.api

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.{Vertx, VertxOptions}
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.StaticHandler

import scala.concurrent.Future

class StaticResourcesHandler extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    router
      .route("/static/*")
      .handler(
        StaticHandler
          .create()
          .setIndexPage("index.html") // this is the default behavior
      )


    vertx
      .createHttpServer
      .requestHandler(router.accept _)
      .listenFuture(7777, "0.0.0.0")
  }
}
