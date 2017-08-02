package controllers

import java.nio.ByteBuffer

import akka.util.ByteString
import boopickle.Default.Unpickle
import play.api.mvc.{Controller, RawBuffer, Request}

import boopickle.Default._ // Implicit pickler
import scala.concurrent.ExecutionContext.Implicits.global

trait ServiceController extends Controller {
  /**
    * Helper for internal routing
    * @param path
    * @param request
    * @param router
    * @return
    */
  protected def internalRoute(path: String, request: Request[RawBuffer])(router: => AutowireRouter.Router) = {
    val b: ByteString = request.body.asBytes(parse.UNLIMITED).get
    router(
      autowire.Core.Request(path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes(b.asByteBuffer))
    ).map(buffer => {
      val data = Array.ofDim[Byte](buffer.remaining())
      buffer.get(data)
      Ok(data)
    })
  }
}
