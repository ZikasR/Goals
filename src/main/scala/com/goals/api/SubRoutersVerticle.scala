package com.goals.api

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router

import scala.concurrent.Future

class SubRoutersVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val restAPI = Router.router(vertx)

    restAPI
      .get("/products/:productID")
      .handler(_.response
        .setChunked(true)
        .write("""{name: "the product"}""")
        .end
      )

    restAPI
      .put("/products/:productID")
      .handler(_.response
        .end
      )

    restAPI
      .delete("/products/:productID")
      .handler(_.response
        .end
      )

    val mainRouter = Router.router(vertx)

    // Handle static resources
    mainRouter.route("/static/*").handler(_ => {
      println("static called")
    })

    mainRouter.route("/.*\\.templ").handler(_ => {
      println("templ called")
    })

    mainRouter.mountSubRouter("/productsAPI", restAPI)

    vertx
      .createHttpServer()
      .requestHandler(mainRouter.accept _)
      .listenFuture(8777, "0.0.0.0")
  }
}
