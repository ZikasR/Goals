package com.goals.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.goals.domaine.Goal
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.BodyHandler

import scala.concurrent.Future

class GoalVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)

    val router = Router.router(vertx)

    router
      .route
      .handler(BodyHandler.create())

    router
      .post("/goals/")
      .handler(rc => {

        val body = rc.getBodyAsJson()

        val goal = mapper.readValue(body.get.toString, classOf[Goal])
        println(s"goal: $goal")

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
