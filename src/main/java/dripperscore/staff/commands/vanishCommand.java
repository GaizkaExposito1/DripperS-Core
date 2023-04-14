package dripperscore.staff.commands;

import dripperscore.DripperS_Core;
import dripperscore.lang.Lang;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class vanishCommand implements CommandExecutor {
    @Getter
    private final DripperS_Core core;
    private final HashMap<UUID,Boolean> vanished;
    String commandType;
    ArrayList<Player> invisible_List = new ArrayList<Player>();

    public vanishCommand(DripperS_Core core,String commandType) {
        this.core = core;
        this.vanished = new HashMap<>();
        this.commandType = commandType;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(commandType == "vanishlist"){
            if(sender instanceof  Player){
                Player player = (Player) sender;
                if(player.hasPermission("dripperscore.*") ||player.hasPermission("dripperscore.staffmode.*")
                        ||player.hasPermission("dripperscore.staffmode.vanishlist")){
                    String vanishPlayersList = Lang.STAFF_VANISH_LIST + "" ;
                    player.sendMessage(Lang.TITLE.toString() + invisible_List.size() );
                    for (Player people : invisible_List){
                        vanishPlayersList = vanishPlayersList + people.getName() + ", ";
                    }
                    player.sendMessage(Lang.TITLE.toString() + vanishPlayersList);
                }
            }
        } else if (commandType == "vanish") {
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
                                player.sendMessage(Lang.TITLE.toString() + Lang.NO_ONLINE);
                            }
                        }else{
                            player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
                        }
                    }else{
                        if(!invisible_List.contains(player)){
                            vanish(player);
                        }else{
                            unVanish(player);
                        }
                    }

                }else{
                    sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
                }
            }else{
                Bukkit.getLogger().info(Lang.TITLE.toString() + Lang.PLAYER_ONLY);
            }
        }
        return true;
    }

    private void vanish(Player player){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.hidePlayer(player);
        }
        invisible_List.add(player);
        player.sendMessage(Lang.TITLE.toString() + Lang.STAFF_VANISHED);
    }
    private void unVanish(Player player){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.showPlayer(player);
        }
        invisible_List.remove(player);
        player.sendMessage(Lang.TITLE.toString() + Lang.STAFF_UNVANISHED);
    }
    private void vanishOther(Player target, Player player, String username){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.hidePlayer(target);
        }
        invisible_List.add(target);
        player.sendMessage(Lang.TITLE.toString() + Lang.PLAYER_VANISHED.toString().replace("%t", target.getName()));
        target.sendMessage(Lang.TITLE.toString() + Lang.STAFF_VANISHED);
    }
    private void unVanishOther(Player target, Player player, String username){
        for (Player people : Bukkit.getOnlinePlayers()){
            people.showPlayer(target);
        }
        invisible_List.remove(target);
        player.sendMessage(Lang.TITLE.toString() + Lang.PLAYER_UNVANISHED.toString().replace("%t", target.getName()));
        target.sendMessage(Lang.TITLE.toString() + Lang.STAFF_UNVANISHED);
    }
}








/**
 *                     //ESTO ES UNA COMPROBACION
 *                     String listaOnline= "&7Online: ";
 *                     String listaOffline= "&8Offline: ";
 *                     for (Player people : Bukkit.getOnlinePlayers()){
 *                         listaOnline= listaOnline + people.getName()+", ";
 *                         //player.sendMessage("Online: " + people.getName().toString());
 *                     }
 *                     for (OfflinePlayer people : Bukkit.getOfflinePlayers()){
 *                         listaOffline= listaOffline + people.getName()+", ";
 *                         //player.sendMessage("OffLine: " + people.getName().toString());
 *                     }
 *                     player.sendMessage( Lang.TITLE.toString() + listaOnline);
 *                     player.sendMessage( Lang.TITLE.toString() + listaOffline);
 */