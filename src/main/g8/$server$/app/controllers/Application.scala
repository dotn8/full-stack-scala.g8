package controllers

import javax.inject._

import play.api.mvc._
import $shared$.Messages

class Application @Inject() extends Controller {

  def index = Action {
    Ok(views.html.index(Messages.itWorks))
  }
}
