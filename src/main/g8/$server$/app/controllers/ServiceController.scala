package controllers

import java.nio.ByteBuffer
import javax.inject._

import akka.util.ByteString
import boopickle.Default.Unpickle
import play.api._
import play.api.mvc._

import boopickle.Default._ // Implicit pickler
import scala.concurrent.ExecutionContext.Implicits.global

class ServiceController(val parsers: PlayBodyParsers, val cc: ControllerComponents) extends AbstractController(cc) {
  /**
    * Helper for internal routing
    * @param path
    * @param request
    * @param router
    * @return
    */
  protected def internalRoute(path: String, request: Request[RawBuffer])(router: => AutowireRouter.Router) = {
    val b: ByteString = request.body.asBytes(parsers.UNLIMITED).get
    router(
      autowire.Core.Request(path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes(b.asByteBuffer))
    ).map(buffer => {
      val data = Array.ofDim[Byte](buffer.remaining())
      buffer.get(data)
      Ok(data)
    })
  }
}
