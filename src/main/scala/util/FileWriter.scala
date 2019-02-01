package util

import java.io.{File, PrintWriter}

/**
  * Obsługa zapisywania do pliku
  */
object FileWriter {

  def write(pathname: String, text: String): Unit = {
    val writer = new PrintWriter(new File(pathname))

    writer.write(text)
    writer.close()
  }

}
