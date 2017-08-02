package controllers

import java.nio.ByteBuffer

import boopickle.Default.{Pickle, Unpickle}
import boopickle.Pickler

object AutowireRouter extends autowire.Server[ByteBuffer, Pickler, Pickler] {

  override def read[R: Pickler](p: ByteBuffer) = Unpickle[R].fromBytes(p)

  override def write[R: Pickler](r: R) = Pickle.intoBytes(r)
}
