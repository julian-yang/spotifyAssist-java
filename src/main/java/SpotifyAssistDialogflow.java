import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import com.tmsdurham.dialogflow.*;
import com.tmsdurham.actions.*;
import java.util.logging.Logger;
import kotlin.jvm.functions.Function1;
import main.java.com.tmsdurham.dialogflow.sample.DialogflowAction;
import spark.Request;
import spark.Response;

public class SpotifyAssistDialogflow {

  private static final Logger logger = Logger.getLogger("SpotifyAssistDialogflow");

  enum Intent {
    WELCOME("input.welcome"),
    ENABLE_SHUFFLE("playback.shuffle.on");

    private final String intentName;

    Intent(String name) {
      this.intentName = name;
    }
  }

  private static Map<String, Function1<DialogflowApp, Void>> intentMap =
      ImmutableMap.<String, Function1<DialogflowApp, Void>>builder()
          .put(Intent.WELCOME.intentName, SpotifyAssistDialogflow::welcome)
          .put(Intent.ENABLE_SHUFFLE.intentName, SpotifyAssistDialogflow::enableShuffle)
          .build();

  static Void welcome(DialogflowApp app) {
    ResponseWrapper<DialogflowResponse> response = app.ask("Hello world?");
    new Env().lookup();
    return null;
  }

  static class Env {

    String lookup() {

      return "";
    }

    String test;
  }

  static Void enableShuffle(DialogflowApp app) {
    app.tell("Turning on shuffle");
    try {
      FileReader fileReader = new FileReader(".env.txt");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuilder stringBuilder = new StringBuilder();
      bufferedReader.lines().map((line) -> stringBuilder.append(line));
      String json = stringBuilder.toString();
      Env test = new Gson().fromJson(json, Env.class);
      logger.warning(test.test);

    } catch (FileNotFoundException ex) {
      logger.severe("Could not find file");
    } catch (IOException ex) {

    }
    return null;
  }

  public static String handlePost(Request request, Response response) throws IOException {
    DialogflowAction action = new DialogflowAction(request.raw(), response.raw());
    action.handleRequest(intentMap);
//    ResponseWrapper<DialogflowResponse> responseWrapper = action.getApp().tell("Test123");
    response.raw().flushBuffer();
    return "noop";
    //    action.handleRequest(intentMap);
  }
}
