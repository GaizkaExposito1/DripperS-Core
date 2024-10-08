package dripperscore.MojanApi;

import com.google.gson.Gson;
import dripperscore.lang.Lang;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class api {

    private static final String MOJANG_API_BASE_URL = "https://api.mojang.com";
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static String getUUID(String username) throws IOException {
        String url = MOJANG_API_BASE_URL + "/users/profiles/minecraft/" + username;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                MojangProfile profile = gson.fromJson(response.body().string(), MojangProfile.class);
                return profile.getId();
            }
        }

        return null;
    }

    private static class MojangProfile {
        private String id;

        public String getId() {
            return id;
        }
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        try {
            String username = "exampleUser";
            String uuid = getUUID(username);

            if (uuid != null) {
                System.out.println("UUID de " + username + ": " + uuid);
            } else {
                System.out.println("No se pudo obtener la UUID para " + username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
