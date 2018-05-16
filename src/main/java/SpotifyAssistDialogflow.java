import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import com.tmsdurham.dialogflow.*;
import com.tmsdurham.actions.*;
import main.java.com.tmsdurham.dialogflow.sample.DialogflowAction;
import spark.Request;
import spark.Response;

public class SpotifyAssistDialogflow {
  private static Map<String, Function<DialogflowApp, Void>> intentMap =
      ImmutableMap.<String, Function<DialogflowApp, Void>>builder()
          .put("WELCOME", (app) -> welcome(app))
          .build();

  static Void welcome(DialogflowApp app) {
    ResponseWrapper<DialogflowResponse> response = app.tell("Hello world!");
    return null;
  }

  public static String handlePost(Request request, Response response) throws IOException {

    DialogflowAction action = new DialogflowAction(request.raw(), response.raw());
    ResponseWrapper<DialogflowResponse> responseWrapper = action.getApp().tell("Test123");
    response.raw().flushBuffer();
    return "noop";
    //    action.handleRequest(intentMap);
  }
}
