package dripperscore.staff.listeners;

import dripperscore.DatabaseManager;
import dripperscore.DripperS_Core;
import dripperscore.entities.User;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.UUID;

public class joinLeftListeners implements Listener {
    @Getter
    private final DripperS_Core core;
    public joinLeftListeners(DripperS_Core core) {
        this.core = core;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        String playerName = e.getPlayer().getName();
        String playerUUID = e.getPlayer().getUniqueId().toString();
        InetSocketAddress playerIp = e.getPlayer().getAddress();

        String playerIpInString = playerIp.toString();

        core.dbManager.addLog("JOIN", "The User: "+ playerName + "Has Joined");

        //TODO: CMPROBAR SI SE LLEGA MENSAJE A LOS OTROS USUARIOS
        e.setJoinMessage(playerName +" Ha entrado al servidor");

        User newuser = new User(playerUUID,playerName, LocalDateTime.now(),playerIpInString,playerIpInString);

        core.dbManager.SaveUser(newuser);

        //User joinedUser = core.dbManager.GetUserByUUID(newuser.getUuid());
        if(newuser.getFreezed()){
            e.getPlayer().setWalkSpeed(0);
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 200));
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
            e.getPlayer().sendMessage(ChatColor.GRAY+"Te saliste mientras estabas "+ChatColor.RED+"congelado"+ChatColor.GRAY+", por favor "+ChatColor.RED+"avisa"+ChatColor.GRAY+" a un staff para ser descongelado");
        }

    }


}
