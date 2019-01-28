package models

import com.themillhousegroup.scoup.Scoup
import play.api.libs.json.{Json, OWrites}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

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
    * Parsuje stronę html do obiektu wpisu
    *
    * @param url link do postu na bash.org.pl
    */
  def fromURL(url: String)(implicit ec: ExecutionContext): BashEntry = {
    val result = Scoup.parse(url).map { doc =>
      BashEntry(
        doc.select(".qid.click").first.ownText.tail.toLong,
        doc.select(".points").first.ownText.toLong,
        doc.select(".post-content.post-body").first.ownText
      )
    }

    Await.result(result, 10.seconds)
  }
}
