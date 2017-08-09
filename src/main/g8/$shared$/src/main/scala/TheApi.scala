package $shared$

trait TheApi {
  def insert(todo: Todo): Int
  def insertAll(todos: List[Todo]): Seq[Int]
  def update(todo: Todo): Int
  def delete(id: Int): Int
  def getAll(): List[Todo]
  def getById(empId: Int): Option[Todo]
}
