package com.goals.api

import com.goals.domaine.Goal
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router

import scala.concurrent.Future

class GoalVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    router
        .post("/goals/")
        .handler(rc => {

          val body = rc.getBodyAsJson()
          val goal = body.get.mapTo(classOf[Goal])

          rc.response
            .setChunked(true)
            .setStatusCode(201)
            .write(s"created : $body")
            .end

        })


    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(6666, "0.0.0.0")

  }

}
