package com.goals.api

import io.vertx.core.http.HttpMethod
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.concurrent.Future

class HttpVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    router
      .route()
      .order(-1)
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

    router
      .route()
      .path("/goals/multipleMethods/:description/")
      .method(HttpMethod.POST)
      .method(HttpMethod.PUT)
      .handler(routingContext => {
        val description = routingContext.request.getParam("description")
        routingContext
          .response
          .end(s"this is a route that match for the 2 http methods : POST & PUT \n and here is the description: ${description.get} \n and the http method is ${routingContext.request().method()}")
      })

    router
      .route("/path/withOrder/")
      .handler(rc => {
        rc.response()
          .write("first handler\n")
        rc.next()
      })

    router
      .route("/path/withOrder/")
      .order(2)
      .handler(rc => {
        rc.response
          .write("second handler\n")
        rc.next()
      })

    router
      .route("/path/withOrder/")
      .handler(_.response
        .write("third handler\n")
        .end()
      )

    //curl -X GET http://localhost:8666/test/mime -H "Content-Type:application/json"
    router
      .get("/test/mime")
      .consumes("*/json")
      .handler(_.response()
        .write("example mime")
        .end()
      )

    // KO: curl -X GET http://localhost:8666/test/mime/produce -H "Accept:application/xml"
    // OK: curl -X GET http://localhost:8666/test/mime/produce -H "Accept:application/json"
    router
        .get("/test/mime/produce")
        .produces("application/json")
        .handler(_.response
            .putHeader("content-type", "application/json")
            .write("{data: 'Hello World in json'}")
            .end
        )

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8666, "0.0.0.0")
  }
}
