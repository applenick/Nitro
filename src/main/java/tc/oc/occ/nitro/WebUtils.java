package tc.oc.occ.nitro;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class WebUtils {

  private static final String USERNAME_API = "https://api.ashcon.app/mojang/v2/user/";;

  public static CompletableFuture<UUID> getUUID(String input) {
    return getProfile(input)
        .thenApplyAsync(
            profile -> {
              String uuid = profile.get("uuid").getAsString();
              return UUID.fromString(uuid);
            });
  }

  private static CompletableFuture<JsonObject> getProfile(String input) {
    return CompletableFuture.supplyAsync(
        () -> {
          JsonObject obj = null;
          HttpURLConnection url;
          try {
            url =
                (HttpURLConnection)
                    new URL(USERNAME_API + Preconditions.checkNotNull(input)).openConnection();

            url.setRequestMethod("GET");
            url.setRequestProperty("User-Agent", "Nitro");
            url.setRequestProperty("Accept", "application/json");
            url.setInstanceFollowRedirects(true);
            url.setConnectTimeout(10000);
            url.setReadTimeout(10000);

            StringBuilder data = new StringBuilder();
            try (final BufferedReader br =
                new BufferedReader(
                    new InputStreamReader(url.getInputStream(), StandardCharsets.UTF_8))) {
              String line;
              while ((line = br.readLine()) != null) {
                data.append(line.trim());
              }
              obj = new Gson().fromJson(data.toString(), JsonObject.class);
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          return obj;
        });
  }
}
