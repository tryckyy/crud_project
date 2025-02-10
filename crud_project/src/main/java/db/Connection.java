package db;

import java.io.IOException;
import java.util.Properties;

public class Connection {

  // Renvoie l'uri de connection à la base de donnée
  public static String connect() {
    Properties properties = new Properties();
    try {
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String uri = properties.getProperty("mongodb.uri");

    return uri;

  }
}
