import play.api.libs.json.Json
import util.Bash

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

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
    val maxPosts = Bash.getMax

    args(0).toInt match {

      case n if n > maxPosts =>
        println(s"Cant find $n posts. There are only $maxPosts posts to fetch.")

      case n if n <= 0 =>
        println(s"Number of posts must be positive.")

      case n =>
        val posts = Await.result(Bash.getLast(n), 30.seconds)

        println(Json.prettyPrint(Json.toJson(posts)))

    }
  }
}
