import io.kuzzle.sdk.Kuzzle;
import java.util.ArrayList;
import io.kuzzle.sdk.protocol.WebSocket;
import io.kuzzle.sdk.coreClasses.responses.Response;
import java.util.*;

public class SnippetTest {
  private static Kuzzle kuzzle;

  public static void main(String[] argv) {
    try {
      kuzzle = new Kuzzle(new WebSocket("kuzzle"));
      kuzzle.connect();
      [snippet-code]
      System.out.println("Error");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (kuzzle != null) {
        kuzzle.disconnect();
      }
    }
  }
}