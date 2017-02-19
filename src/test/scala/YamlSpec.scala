import java.util

import org.scalatest._
import org.scalatest.Matchers._
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import collection.JavaConverters._

import net.jcazevedo.moultingyaml._
import CustomYamlProtocol._

class YamlSpec extends FlatSpec {

  val personYAML : String =
    """|name: Bob
       |age: 26
       |skills:
       |- java
       |- scala""".stripMargin

  val teamYAML : String =
    """
      |name: amazing team
      |scrum:
      |  name: John
      |  age: 34
      |  skills:
      |  - project management
      |  - problem solving
      |developers:
      |- name: Bob
      |  age: 26
      |  skills:
      |  - java
      |  - scala
      |- name: Alice
      |  age: 24
      |  skills:
      |  - scala
      |  - js
      |- name: Charlie
      |  age: 22
      |  skills: []
    """.stripMargin

  it should "parse a yaml containing a Person using snakeYAML" in {
    val yaml = new Yaml(new Constructor(classOf[PersonSnake]))
    val person = yaml.load(personYAML).asInstanceOf[PersonSnake]
    println(person)

    val expectedPerson = new PersonSnake()
    expectedPerson.name = "Bob"
    expectedPerson.age = 26
    expectedPerson.skills = new util.ArrayList[String](List[String]("java","scala").asJava)

    println(expectedPerson)

    person.name shouldEqual expectedPerson.name
    person.age shouldEqual expectedPerson.age
    person.skills shouldEqual expectedPerson.skills
  }

  it should "parse a yaml containing a Person using moultingYaml" in {

    val bob = personYAML.parseYaml.convertTo[Person]
    println(bob)

    val expectedBob = Person("Bob", 26, Seq("java","scala"))

    bob shouldEqual expectedBob
  }

  it should "produce a yaml containing a Person using moultingYaml" in {
    val bob = Person("Bob", 26, Seq("java","scala"))
    val yaml = bob.toYaml.prettyPrint

    yaml.trim shouldEqual personYAML.trim
  }

  it should "parse a yaml containing a Team using moultingYaml" in {

    val team = teamYAML.parseYaml.convertTo[Team]

    val expectedTeam = Team(
      name = "amazing team",
      scrum = Person("John", 34, Seq("project management","problem solving")),
      developers = List(
        Person("Bob", 26, Seq("java","scala")),
        Person("Alice", 24, Seq("scala","js")),
        Person("Charlie", 22, Seq())
      )
    )

    team shouldEqual expectedTeam
  }

  it should "produce a yaml containing a Team using moultingYaml" in {

    val team = Team(
      name = "amazing team",
      scrum = Person("John", 34, Seq("project management","problem solving")),
      developers = List(
        Person("Bob", 26, Seq("java","scala")),
        Person("Alice", 24, Seq("scala","js")),
        Person("Charlie", 22, Seq())
      )
    )

    val yaml = team.toYaml.prettyPrint

    yaml.trim shouldEqual teamYAML.trim

  }


}
