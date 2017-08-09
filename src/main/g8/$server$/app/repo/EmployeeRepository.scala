package repo

import javax.inject.{Inject, Singleton}

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future
import $shared$._

@Singleton()
class TodoRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends TodoTable with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  def insert(todo: Todo): Future[Int] = db.run {
    empTableQueryInc += todo
  }

  def insertAll(todos: List[Todo]): Future[Seq[Int]] = db.run {
    empTableQueryInc ++= todos
  }

  def update(todo: Todo): Future[Int] = db.run {
    empTableQuery.filter(_.id === todo.id).update(todo)
  }

  def delete(id: Int): Future[Int] = db.run {
    empTableQuery.filter(_.id === id).delete
  }

  def getAll(): Future[List[Todo]] = db.run {
    empTableQuery.to[List].result
  }

  def getById(empId: Int): Future[Option[Todo]] = db.run {
    empTableQuery.filter(_.id === empId).result.headOption
  }

  def ddl = empTableQuery.schema

}

private[repo] trait TodoTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._

  protected def getTableQuery() = {
    val result = TableQuery[TodoTable]
    db.run(result.schema.create)
    result
  }

  lazy protected val empTableQuery = getTableQuery()
  lazy protected val empTableQueryInc = empTableQuery returning empTableQuery.map(_.id)

  private[TodoTable] class TodoTable(tag: Tag) extends Table[Todo](tag, "todo") {
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    val name: Rep[String] = column[String]("name", O.SqlType("VARCHAR(200)"))
    val email: Rep[String] = column[String]("email", O.SqlType("VARCHAR(200)"))
    val companyName: Rep[String] = column[String]("company_name")
    val position: Rep[String] = column[String]("position")

    def emailUnique = index("email_unique_key", email, unique = true)

    def * = (name, email, companyName, position, id.?) <>(Todo.tupled, Todo.unapply)
  }

}
