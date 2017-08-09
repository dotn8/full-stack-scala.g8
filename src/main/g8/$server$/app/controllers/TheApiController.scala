package controllers

import play.api._
import play.api.mvc._
import boopickle.Default._
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

import repo._
import $shared$.TheApi

/**
  * API Controller
  * Each request will create a SampleApiImpl instance.
  * It's necessary if you want to set the user in the constructor, otherwise you can use singleton
  */
class TheApiController @Inject() (val empRepository: EmployeeRepository, parsers: PlayBodyParsers, cc: ControllerComponents) extends ServiceController(parsers, cc) {
  def sampleApi(path: String) = Action.async(parsers.raw) { implicit request =>
    internalRoute(path, request) {
      AutowireRouter.route[TheApi](new TheApiImpl(empRepository))
    }
  }
}
