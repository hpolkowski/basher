package models

import com.themillhousegroup.scoup.Scoup
import com.themillhousegroup.scoup.ScoupImplicits._
import play.api.libs.json.{Json, OWrites}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Obiekt pojedynczego wpisu na bash.org.pl
  *
  * @param id       identyfikator
  * @param points   ilość punktów
  * @param content  treść wpisu
  */
case class BashEntry (
  id: Long,
  points: Long,
  content: String
)

/**
  * Obiekt towarzyszący zawierający niezbędne metody
  */
object BashEntry {
  implicit val bashEntry_Writes: OWrites[BashEntry] = Json.writes[BashEntry]

  /**
    * Pobiera ilość wszystkich postów na stronie
    *
    * @param url link do strony
    */
  def maxPosts(url: String)(implicit ec: ExecutionContext): Future[Int] = {
    Scoup.parse(url).map { doc =>
      doc.select("#foot b").first.text.toInt
    }
  }

  /**
    * Parsuje stronę html do obiektu wpisu
    *
    * @param url link do strony
    * @param n   ilość wpisów do pobrania
    */
  def fromURL(url: String, n: Int)(implicit ec: ExecutionContext): Future[Iterable[BashEntry]] = {
    Scoup.parse(url).map { doc =>
      doc.select(".q.post").take(n).map { post =>
        BashEntry(
          post.select(".qid.click").first.text.tail.toLong,
          post.select(".points").first.text.toLong,
          post.select(".post-content.post-body").first.text
        )
      }
    }
  }
}
