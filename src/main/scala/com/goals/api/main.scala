package com.goals.api 

import io.vertx.scala.core._

object Hello extends App {

    val v = Vertx.vertx()
    v.deployVerticle(s"scala:${classOf[HttpVerticle].getName}")
    v.deployVerticle(s"scala:${classOf[SubRoutersVerticle].getName}")
    v.deployVerticle(s"scala:${classOf[ErrorHandlingVerticle].getName}")
    v.deployVerticle(s"scala:${classOf[BodyHandlingVerticle].getName}")
    v.deployVerticle(s"scala:${classOf[AuthenticationAuthorisationHandlingVerticle].getName}")
    v.deployVerticle(s"scala:${classOf[StaticResourcesHandler].getName}")
    v.deployVerticle(s"scala:${classOf[ResponseTimeHandlerVerticle].getName}")
    v.deployVerticle(s"scala:${classOf[SockJSHandlerVerticle].getName}")

}