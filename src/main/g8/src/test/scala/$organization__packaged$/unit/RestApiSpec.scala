package $organization$.unit

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.testkit.TestActorRef
import $organization$.RestMessageProtocol.{Request, Response}
import $organization$.{RestApiRoutes, RestMessageJsonProtocol, RestRequestHandler}
import org.scalatest.{Matchers, WordSpec}
import spray.json._

import scala.util.Success

class RestApiSpec extends WordSpec
  with Matchers
  with ScalatestRouteTest
  with RestMessageJsonProtocol
  with RestApiRoutes {

  "The restful API" should {
    "handle GET requests" in {
      // example commented to ensure tests pass when testing g8 template
      //
      // val handler: TestActorRef[RestRequestHandler] = TestActorRef[RestRequestHandler](RestRequestHandler.props())
      //
      // Get("api/request") ~> routes(handler) ~> check {
      //   status shouldBe StatusCodes.OK
      //   // responseAs[Response] shouldEqual ???
      // }

      pending
    }

    "handle POST requests" in {
      // example commented to ensure tests pass when testing g8 template
      //
      // val handler: TestActorRef[RestRequestHandler] = TestActorRef[RestRequestHandler](RestRequestHandler.props())
      // val request = Request("12345", "This is a test")
      //
      // Post("api/request", HttpEntity(ContentTypes.`application/json`, request.toJson.prettyPrint)) ~> routes(handler) ~> check {
      //   status shouldBe StatusCodes.OK
      //   Unmarshal(responseEntity).to[Response] onComplete {
      //     case Success(response) ⇒
      //       response.requestId shouldEqual "12345"
      //   }
      // }
      pending
    }
  }
}
