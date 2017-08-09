package controllers

import java.nio.ByteBuffer

import akka.util.ByteString
import boopickle.Default._
import boopickle.Pickler
import play.api.mvc.{Controller, RawBuffer, Request}
import boopickle.Default._
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._

import $shared$._
import repo._

import scala.concurrent.ExecutionContext.Implicits.global

class TheApiImpl(val empRepository: TodoRepository) extends TheApi {
  def insert(todo: Todo): Int = {
    println("Server-side API was called")
    Await.result(empRepository.insert(todo), 10.seconds)
  }

  def insertAll(todos: List[Todo]): Seq[Int] = {
    println("Server-side API was called")
    Await.result(empRepository.insertAll(todos), 10.seconds)
  }

  def update(todo: Todo): Int = {
    println("Server-side API was called")
    Await.result(empRepository.update(todo), 10.seconds)
  }

  def delete(id: Int): Int = {
    println("Server-side API was called")
    Await.result(empRepository.delete(id), 10.seconds)
  }

  def getAll(): List[Todo] = {
    println("Server-side API was called")
    Await.result(empRepository.getAll(), 10.seconds)
  }

  def getById(empId: Int): Option[Todo] = {
    println("Server-side API was called")
    Await.result(empRepository.getById(empId), 10.seconds)
  }
}
