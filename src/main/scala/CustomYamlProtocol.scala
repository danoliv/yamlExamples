import net.jcazevedo.moultingyaml._

object CustomYamlProtocol extends DefaultYamlProtocol {
  implicit val personFormat = yamlFormat3(Person)
  implicit val teamFormat = yamlFormat3(Team)
}