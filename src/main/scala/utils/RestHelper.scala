package utils

/**
  * Contains all REST methods
  */
object RestHelper {

  /**
    * Executes GET method to given address
    *
    * @param url website address
    * @return response from website
    */
  def get(url: String): Option[String] = try {
    Some(scala.io.Source.fromURL(url).mkString)
  } catch {

    case error: Throwable =>
      println(s"Request Error: error occurred while executing get request. ${error.getMessage}")
      None
  }
}
