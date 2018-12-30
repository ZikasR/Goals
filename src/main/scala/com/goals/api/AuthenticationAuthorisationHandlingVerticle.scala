package com.goals.api

import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.shiro.{PropertiesProviderConstants, ShiroAuthRealmType}
import io.vertx.scala.ext.auth.AuthProvider
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.auth.shiro.{ShiroAuth, ShiroAuthOptions}
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler._
import io.vertx.scala.ext.web.sstore.LocalSessionStore

import scala.concurrent.Future

class AuthenticationAuthorisationHandlingVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {

    val router = Router.router(vertx)
    val config = new JsonObject().put(PropertiesProviderConstants.PROPERTIES_PROPS_PATH_FIELD, "classpath:test-auth.properties")

    val authProvider: AuthProvider = ShiroAuth.create(vertx, ShiroAuthOptions()
      .setType(ShiroAuthRealmType.PROPERTIES)
      .setConfig(config)
    )

    router
      .route
      .handler(CookieHandler.create)

    router
      .route
      .handler(SessionHandler.create(LocalSessionStore.create(vertx)))

    router
      .route
      .handler(
        BasicAuthHandler.create(authProvider)
          .addAuthority("role:administrator")
      )


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

    val basicAuthHandler = BasicAuthHandler.create(authProvider)

    // All requests to paths starting with '/private/' will be protected
    router.route("/private/*").handler(basicAuthHandler)

    router.route("/someOtherPath").handler(_.response()
      .end("No authentication needed for this route")
    )

    router.route("/private/somePath").handler((routingContext: io.vertx.scala.ext.web.RoutingContext) => {

      // This will require a login

      // This will have the value true
      val isAuthenticated = routingContext.user() != null

      routingContext.response()
        .end(s"Is Authenticated ? : $isAuthenticated")

    })

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8989, "0.0.0.0")
  }
}
