package com.goals.api


import io.vertx.core.buffer.Buffer
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.sockjs.{SockJSHandler, SockJSHandlerOptions, SockJSSocket}

import scala.concurrent.Future

class SockJSHandlerVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)
    val options = SockJSHandlerOptions()
      .setHeartbeatInterval(2000)

    router
      .route("/myapp/*")
      .handler(
        SockJSHandler.create(vertx, options)
          .socketHandler((sJSs: SockJSSocket) => {
            sJSs.handler((s: Buffer) => sJSs.write(s))
          })
      )

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(9876, "0.0.0.0")
  }
}
