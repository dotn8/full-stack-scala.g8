package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import $shared$.Messages

class HomeController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(Messages.itWorks))
  }
}
