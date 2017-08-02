package controllers

import play.api.mvc.Action
import boopickle.Default._
import $shared$.TheApi

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * API Controller
  * Each request will create a SampleApiImpl instance.
  * It's necessary if you want to set the user in the constructor, otherwise you can use singleton
  */
class TheApiController extends ServiceController {
  def sampleApi(path: String) = Action.async(parse.raw) { implicit request =>
    internalRoute(path, request) {
      AutowireRouter.route[TheApi](new TheApiImpl())
    }
  }
}
