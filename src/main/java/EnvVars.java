import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class EnvVars {

  public String test;
  public String PORT;
  public String SPOTIFY_CLIENT_ID;
  public String SPOTIFY_CLIENT_SECRET;

  private static final Logger logger = Logger.getLogger("EnvLoader");

  public static EnvVars loadFromFile() {
    try {
      FileReader fileReader = new FileReader(ClassLoader.getSystemResource("./.env").getFile());
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuilder stringBuilder = new StringBuilder();
      String str;
      while ((str = bufferedReader.readLine()) != null) {
        stringBuilder.append(str);
      }
      String json = stringBuilder.toString();
      return new Gson().fromJson(json, EnvVars.class);
    } catch (FileNotFoundException ex) {
      logger.severe("Could not find file");
    } catch (IOException ex) {

    }
    return new EnvVars();
  }

  public static EnvVars load() {
    if (System.getenv("PORT") != null) {
      EnvVars envVars = new EnvVars();
      envVars.PORT = System.getenv("PORT");
      return envVars;
    }
    return loadFromFile();
  }
}
