package $shared$

case class Todo(name: String,
  email: String,
  companyName: String,
  position: String,
  id: Option[Int] = None)
