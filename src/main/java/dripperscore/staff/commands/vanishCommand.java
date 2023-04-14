package dripperscore.staff.commands;

import dripperscore.DripperS_Core;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class vanishCommand implements CommandExecutor {
    @Getter
    private final DripperS_Core core;
    private final HashMap<UUID,Boolean> vanished;
    ArrayList<Player> invisible_List = new ArrayList<Player>();

    public vanishCommand(DripperS_Core core) {
        this.core = core;
        this.vanished = new HashMap<>();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String vanishMenssage = core.getConfig().getString("PlayerVanishedext");
        String unVanishMenssage = core.getConfig().getString("PlayerUnVanishedText");
        String notPermissons = core.getConfig().getString("notPermissonsText");

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("dripperscore.*") ||player.hasPermission("dripperscore.staffmode.*")
                    ||player.hasPermission("dripperscore.staffmode.vanish")){
                if(args.length != 0){
                    if(player.hasPermission("dripperscore.staffmode.vanish.others")){
                        String username = args[0];
                        Player target = Bukkit.getServer().getPlayerExact(username);
                        if(target!= null){
                            if(!invisible_List.contains(target)){
                                vanishOther(target,player,username);
                            }else{
                                unVanishOther(target,player,username);
                            }
                        }else{
                            player.sendMessage("ERROR NO ENCONTRADO");
                        }

                    }else{
                        //ERROR NO PERMISSON
                    }
                }else{
                    for (Player people : core.getServer().getOnlinePlayers()){
                        player.sendMessage("Online: " + people.getName().toString());
                    }
                    for (OfflinePlayer people : core.getServer().getOfflinePlayers()){
                        player.sendMessage("OffLine: " + people.getName().toString());
                    }
                    if(!invisible_List.contains(player)){
                        vanish(player);
                    }else{
                        unVanish(player);
                    }
                }

            }else{
                sender.sendMessage(ChatColor.RED+notPermissons);
            }
        }else{
            Bukkit.getLogger().info(command + "an only execute being a player");
        }
        return true;
    }

    private void vanish(Player player){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.hidePlayer(player);
        }

        invisible_List.add(player);
        player.sendMessage("MENSAJE DE QUE ERES IN VISIBLE");
    }
    private void unVanish(Player player){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.showPlayer(player);
        }
        invisible_List.remove(player);
        player.sendMessage("MENSAJE DE QUE ERES VISIBLE");
    }
    private void vanishOther(Player target, Player player, String username){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.hidePlayer(target);
        }
        invisible_List.add(target);
        player.sendMessage("MENSAJE DE QUE has hecho invisible a alguien");
        target.sendMessage("MENSAJE DE QUE ERES IN VISIBLE");
    }
    private void unVanishOther(Player target, Player player, String username){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.showPlayer(target);
        }
        invisible_List.remove(target);
        player.sendMessage("MENSAJE DE QUE has hecho visible a alguien");
        target.sendMessage("MENSAJE DE QUE ERES VISIBLE");
    }
}
