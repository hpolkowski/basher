package util

import models.BashEntry

import scala.collection.immutable
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Bash {
  /**
    * Adres strony z której będą pobierane posty
    */
  private val url = "http://bash.org.pl"

  /**
    * Ilość wpisów na jednej stronie
    */
  private val pageSize = 20

  /**
    * Zwraca maksymalną ilość wpisów znajdujących się w serwisie
    *
    * @return ilość wszystkich wpisów
    */
  def getMax()(implicit ec: ExecutionContext): Int = {
    val result = BashEntry.maxPosts(url)

    Await.result(result, 30.seconds)
  }

  /**
    * Pobiera n najnowszych wpisów z serwisu bash.org.pl
    *
    * @param n ilość wpisów do pobrania
    */
  def getLast(n: Int)(implicit ec: ExecutionContext): Future[immutable.IndexedSeq[BashEntry]] = {
    val pages = math.ceil(n / pageSize.toDouble).toInt

    val futurePosts = (1 to pages).map { page =>
      val size = if(n < page * pageSize) n % pageSize else pageSize

      BashEntry.fromURL(s"$url/latest/?page=$page", size)
    }

    Future.sequence(futurePosts.map(_.transform(Success(_)))).map { posts =>
      posts.flatMap {

      case Success(post) =>
        Some(post)

      case Failure(err) =>
        None

    }.flatten}
  }
}
