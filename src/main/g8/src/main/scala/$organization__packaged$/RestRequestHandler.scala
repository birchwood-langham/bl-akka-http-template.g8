package $organization$

import akka.actor.{Actor, ActorLogging, Props}

class RestRequestHandler extends Actor with ActorLogging {
  import RestMessageProtocol._

  def receive: Receive = {
    case Request(requestId, message) =>
      // do something e.g. print out the message or handle it in some other way
      println(message)
      sender() ! Response(requestId)
  }
}

object RestRequestHandler {
  def props(): Props = Props[RestRequestHandler]
}
