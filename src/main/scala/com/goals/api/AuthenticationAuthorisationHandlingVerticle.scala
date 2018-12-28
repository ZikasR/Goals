package com.goals.api

import io.vertx.scala.ext.auth.AuthProvider
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.auth.oauth2.OAuth2Auth
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.{CookieHandler, SessionHandler, UserSessionHandler}
import io.vertx.scala.ext.web.sstore.LocalSessionStore

import scala.concurrent.Future

class AuthenticationAuthorisationHandlingVerticle extends ScalaVerticle{

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)

    val authProvider: AuthProvider = OAuth2Auth.create(vertx, OAuth2FlowType.AUTH_CODE)

    router
      .route
      .handler(CookieHandler.create)

    router
      .route
      .handler(SessionHandler.create(LocalSessionStore.create(vertx)))

    router
      .route
      .handler(UserSessionHandler.create(authProvider))

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8989, "0.0.0.0")
  }
}
