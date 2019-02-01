import play.api.libs.json.Json
import util.Bash

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Aplikacja
  */
object Application {

  /**
    * Wyszukuje postów i je wypisuje
    *
    * @param n ilość postów do wyszukania
    */
  private def crawl(n: Int) = for {
    posts <- Bash.getLast(n)
  } yield
    println(Json.prettyPrint(Json.toJson(posts)))

  /**
    * Główna klasa projektu
    *
    * @param args arguments from run
    */
  def main(args: Array[String]): Unit = {
    val maxPosts = Bash.getMax
    val numberOfPosts = try {
      Some(args(0).toInt)

    } catch {

      case e: Exception =>
        println("Number of posts must be integer value.")
        None
    }

    numberOfPosts match {

      case Some(n) if n > maxPosts =>
        println(s"Cant find $n posts. There are only $maxPosts posts to fetch.")

      case Some(n) if n <= 0 =>
        println("Number of posts must be positive.")

      case Some(n) =>
        Await.result(crawl(n), 30.seconds)

      case None =>

    }
  }
}
