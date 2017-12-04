package com.birchwoodlangham

import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, PredefinedFromEntityUnmarshallers}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object RestMessageProtocol {
  case class Request(requestId: String, message: String)
  case class ProtoRequest(requestId: String, message: String)
  case class Response(requestId: String)
}

trait RestMessageJsonProtocol extends DefaultJsonProtocol {
  import RestMessageProtocol._
  import language.implicitConversions

  implicit val requestJsonFormat: RootJsonFormat[Request] = jsonFormat2(Request)
  implicit val responseJsonFormat: RootJsonFormat[Response] = jsonFormat1(Response)
}

trait RestMessageByteProtocol {
  import RestMessageProtocol._
  import language.implicitConversions

  implicit val byteUnmarshaller: FromEntityUnmarshaller[ProtoRequest] = {
    PredefinedFromEntityUnmarshallers.byteArrayUnmarshaller map {
      bytes ⇒ ??? // put your code for converting the byte data into the message you require
        /* e.g. If it's a protocol buffer message, we could do something like this:
         *
         * given a protocol buffer defined as:
         *
         *      message MessageProto {
         *        string requestId = 1;
         *        string message = 2;
         *      }
         *
         * After we have created the protobuf Java classes, we can then use them to convert the byte array like this:
         *
         * val proto = MessageProto.parseFrom(bytes)
         * ProtoRequest(proto.getRequestId, proto.getMessage)
         *
         * The request is what we return for the implicit conversion
         *
         * When we want to receive binary data on the http request, we will need to extend this trait in order to implicitly convert
         * the http payload to the object we want
         */
    }
  }
}
