import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.dom
import org.scalajs.dom.document
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.raw.Event

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSON

import autowire._
import $shared$._
import boopickle.Default._

object TheApp extends js.JSApp {
  /**
    * Ajax Request to server, updates data state with number
    * of requests to count.
    * @param data
    */
  def countRequest(data: Var[String]) = {
    val url = "http://localhost:9000/count"
    Ajax.get(url).onSuccess { case xhr =>
      data := JSON.parse(xhr.responseText).count.toString
    }
    val a = AjaxClient[TheApi].doThing(1, "a").call()
    a.foreach(str => {
      println(str)
    })
  }

  def autowireRequest(data: Var[String]) = {
    val a = AjaxClient[TheApi].doThing(1, "a").call()
    println(a)
  }

  @dom
  def render = {
    val data = Var("")
    countRequest(data) // initial population
    <div>
      <button onclick={event: Event => countRequest(data) }>
        Boop
      </button>
      From Play: The server has been booped { data.bind } times. Message: {$shared$.Messages.itWorks}.
    </div>
  }

  def main(): Unit = {
    dom.render(document.body, render)
  }
}
