package $organization$

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.config.{Config, ConfigFactory}

import concurrent.duration._
import language.postfixOps
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}


trait RestApiRoutes extends RestMessageByteProtocol with RestMessageJsonProtocol with SprayJsonSupport {
  import akka.pattern.ask
  import RestMessageProtocol._

  implicit val timeout: Timeout = Timeout(5 seconds)

  def routes(requestHandler: ActorRef): Route = {
    path("api" / "request") {
      get {
        ???
        // e.g. requestHandler ! request
      } ~
      post {
        entity(as[Request]) {
          // The RestMessageJsonProtocol should handle the conversion between Json to case class, so our handler then uses the akka ask pattern to forward the message and await the response
          // We use the future handler to determine if the request succeeded or failed, then if the request succeeded, then we check the response to determine if the request was valid and
          // an appropriate response was returned.
          // If everything worked out, we send the response back with the OK status code, and the response, otherwise we respond with a bad request
          // If the ask failed, then we know something went wrong with our service, and we respond with the InternalServerError and we provide the exception in the body
          request ⇒
            onComplete(requestHandler ? request) {
              case Success(response) ⇒ response match {
                  case r: Response ⇒ complete(StatusCodes.OK, r)
                  case _ ⇒ complete(StatusCodes.BadRequest)
                }
              case Failure(e) ⇒ complete(StatusCodes.InternalServerError, e)
            }
        }
      }
    } ~
    pathPrefix("api" / "request") {
      get {
        path(Segment) {
          id ⇒ ???  // do something like return the details of the request with the given id
        }
      }
    } ~
    path("api" / "request" / "proto") {
      post {
        // this route will accept an ContentTypes.`application/octet-stream` message body which should be a byte array
        // it will use the RestMessageByteProtocol implicit conversions to convert the byte array into an object we can use
        entity(as[ProtoRequest]) {
          request ⇒
            onComplete(requestHandler ? request) {
              case Success(response) ⇒ response match {
                case r: Response ⇒ complete(StatusCodes.OK, r)
                case _ ⇒ complete(StatusCodes.BadRequest)
              }
              case Failure(e) ⇒ complete(StatusCodes.InternalServerError, e)
            }
        }
      }
    }
  }

  def run(): Unit = {
    val config: Config = ConfigFactory.load()

    implicit val system: ActorSystem = ActorSystem(config.getString("akka.actor.system.name"))
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    implicit val timeout: Timeout = Timeout(config.getInt("akka.actor.timeout-ms") milliseconds)

    val handler: ActorRef = system.actorOf(RestRequestHandler.props())

    val source: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
       Http().bind(
         interface = config.getString("akka.http.bind.host"),
         port = config.getInt("akka.http.bind.port")
       )

    val sink = Sink.foreach[Http.IncomingConnection](_.handleWith(routes(handler)))
    source.to(sink).run()
  }
}
