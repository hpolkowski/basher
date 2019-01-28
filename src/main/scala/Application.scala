import models.BashEntry
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Main application
  */
object Application {

  /**
    * Main project class
    *
    * @param args arguments from run
    */
  def main(args: Array[String]): Unit = {
    val bashEntry = BashEntry.fromURL("http://bash.org.pl/4862636")

    println(Json.toJson(bashEntry))
  }
}
