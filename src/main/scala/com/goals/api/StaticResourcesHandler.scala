package com.goals.api

import io.vertx.core.http.HttpHeaders
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.{ErrorHandler, StaticHandler}

import scala.concurrent.Future

class StaticResourcesHandler extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)


    router
      .route()
      .handler(rc => {
        rc.response().putHeader(HttpHeaders.CONTENT_TYPE.toString, "text/html")
        rc.next()
      })

    router
      .route()
      .failureHandler(ErrorHandler.create("error-handler-template.html"))

    router
      .route("/static/*")
      .handler(
        StaticHandler
          .create()
          .setIndexPage("index.html") // this is the default behavior
      )

    router.route("/bob/*").handler(rc => {
      rc.fail(404)
    })


    vertx
      .createHttpServer
      .requestHandler(router.accept _)
      .listenFuture(7777, "0.0.0.0")
  }
}
