package util

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Konfiguracja aplikacji
  */
class AppConfig {
  private val config: Config =  ConfigFactory.load().getConfig("config")

  val `output-file`: String = config.getString("output.file.path")
}

/**
  * Obiekt towarzyszący klasy AppConfig
  */
object AppConfig {
  val appConfig = new AppConfig
}
