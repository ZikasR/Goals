package com.goals.api

import io.vertx.ext.auth.{AuthProvider, User}
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.{CookieHandler, SessionHandler, UserSessionHandler}
import io.vertx.scala.ext.web.sstore.LocalSessionStore

import scala.concurrent.Future

class AuthenticationAuthorisationHandlingVerticle extends ScalaVerticle{

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    val authProvider: AuthProvider = ShiroAuth.create(vertx, ShiroAuthRealmType.PROPERTIES, new JsonObject());

    router
      .route
      .handler(CookieHandler.create)

    router
      .route
      .handler(SessionHandler.create(LocalSessionStore.create(vertx)))

    router
      .route
      .handler(UserSessionHandler.create(authProvider))
  }
}
