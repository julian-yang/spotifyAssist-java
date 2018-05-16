import static spark.Spark.*;

import java.util.logging.Logger;
import spark.Spark;

public class Main {
  public static void main(String[] args) {
    Logger logger = Logger.getLogger("Main.class");

    port(getHerokuAssignedPort());
    get("/hello", (req, res) -> "Hello world");
    post("/webhook", (req, res) -> {
      SpotifyAssistDialogflow.handlePost(req, res);
      logger.warning("request: " + req.body());
      return "string";
    });
    exception(Exception.class, (exception, request, response) -> {
      exception.printStackTrace();
    });
  }

  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 8080;
  }
}
