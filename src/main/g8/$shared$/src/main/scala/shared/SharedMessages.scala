package $shared$

object SharedMessages {
  def itWorks = "It works!"
}

trait MyApi{
  def doThing(i: Int, s: String): Seq[String]
}
