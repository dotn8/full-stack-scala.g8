package $shared$

trait TheApi {
  def insert(employee: Employee): Int
  def insertAll(employees: List[Employee]): Seq[Int]
  def update(employee: Employee): Int
  def delete(id: Int): Int
  def getAll(): List[Employee]
  def getById(empId: Int): Option[Employee]
}
