package com.goals.api 

import io.vertx.scala.core._

object Hello extends App {

    val v = Vertx.vertx()
    v.deployVerticle(s"scala:${classOf[HttpVerticle].getName}")
    v.deployVerticle(s"scala:${classOf[SubRoutersVerticle].getName}")
}