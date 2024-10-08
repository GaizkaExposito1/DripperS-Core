package dripperscore.MojanApi;

import dripperscore.DripperS_Core;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class pruebacommand implements CommandExecutor {

    @Getter
    private final DripperS_Core core;



    public pruebacommand(DripperS_Core core) {
        this.core = core;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar si el comando fue ejecutado por un jugador
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;

        // Verificar si se proporcionó un nombre de usuario
        if (args.length < 1) {
            player.sendMessage("Uso correcto: /getuuid <nombre_usuario>");
            return true;
        }

        String username = args[0];

        try {
            // Obtener la UUID del usuario
            String uuid = api.getUUID(username);

            if (uuid != null) {
                player.sendMessage("La UUID de " + username + " es: " + uuid);
            } else {
                player.sendMessage("No se pudo obtener la UUID para " + username);
            }
        } catch (IOException e) {
            player.sendMessage("Ocurrió un error al obtener la UUID. Por favor, intenta nuevamente más tarde.");
            e.printStackTrace();
        }

        return true;
    }
}
