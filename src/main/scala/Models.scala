import scala.beans.BeanProperty
import collection.JavaConverters._

class PersonSnake {
  /**
    * With the Snakeyaml Constructor approach shown in the main method,
    * this class must have a no-args constructor.
    */
  @BeanProperty var name: String = null
  @BeanProperty var age: Int = 0
  @BeanProperty var skills: java.util.List[String] = null

  override def toString: String =
    s"name: $name, age: $age, skills: ${skills.asScala.mkString(", ")}"

}

case class Person(name: String, age: Int, skills: Seq[String])

case class Team(name: String, scrum: Person, developers: List[Person])
