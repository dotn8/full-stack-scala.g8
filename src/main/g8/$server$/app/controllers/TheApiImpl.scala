package controllers

import java.nio.ByteBuffer

import akka.util.ByteString
import boopickle.Default._
import boopickle.Pickler
import play.api.mvc.{Controller, RawBuffer, Request}
import boopickle.Default._
import $shared$.TheApi

import scala.concurrent.ExecutionContext.Implicits.global

class TheApiImpl extends TheApi {
  def doThing(i: Int, s: String): Seq[String] = {
    println("Server-side API was called")
    Seq("This came from the server (#1)", "This came from the server (#2)")
  }
}
