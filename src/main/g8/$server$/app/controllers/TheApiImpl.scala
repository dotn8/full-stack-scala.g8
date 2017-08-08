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

class TheApiImpl(val empRepository: EmployeeRepository) extends TheApi {
  def insert(employee: Employee): Int = {
    println("Server-side API was called")
    Await.result(empRepository.insert(employee), 10.seconds)
  }

  def insertAll(employees: List[Employee]): Seq[Int] = {
    println("Server-side API was called")
    Await.result(empRepository.insertAll(employees), 10.seconds)
  }

  def update(employee: Employee): Int = {
    println("Server-side API was called")
    Await.result(empRepository.update(employee), 10.seconds)
  }

  def delete(id: Int): Int = {
    println("Server-side API was called")
    Await.result(empRepository.delete(id), 10.seconds)
  }

  def getAll(): List[Employee] = {
    println("Server-side API was called")
    Await.result(empRepository.getAll(), 10.seconds)
  }

  def getById(empId: Int): Option[Employee] = {
    println("Server-side API was called")
    Await.result(empRepository.getById(empId), 10.seconds)
  }
}
