import utils.RestHelper

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
    val response = RestHelper.get("http://bash.org.pl")

    response.foreach(println)
  }
}
