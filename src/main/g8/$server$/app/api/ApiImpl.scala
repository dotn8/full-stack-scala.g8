package api

import autowire._
import play.api.libs.json._ // JSON library
import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.functional.syntax._ // Combinator syntax

object MyApiImpl extends MyApi{
  def doThing(i: Int, s: String) = Seq.fill(i)(s)
}
object MyServer extends autowire.Server[String, upickle.Reader, upickle.Writer]{
  def write[Result: Writer](r: Result) = upickle.write(r)
  def read[Result: Reader](p: String) = upickle.read[Result](p)

  val routes = MyServer.route[MyApi](MyApiImpl)
}
