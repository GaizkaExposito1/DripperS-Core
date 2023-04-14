package dripperscore.other.commands;

import dripperscore.DripperS_Core;
import dripperscore.lang.Lang;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.Console;
import java.util.HashMap;
import java.util.UUID;

public class flyCommand implements CommandExecutor {
    @Getter
    private final DripperS_Core core;
    private final HashMap<UUID,Boolean> flyEnabled;
    public flyCommand(DripperS_Core core) {
        this.core = core;
        flyEnabled = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(player.hasPermission("dripperscore.fly")){
                if(args.length != 0){
                    if(player.hasPermission("dripperscore.fly.others")){
                        String username = args[0];
                        Player target = Bukkit.getServer().getPlayerExact(username);
                        if(target!= null){

                            if(!this.flyEnabled.containsKey(target.getUniqueId())){
                                flyOtherEnable(target,player,username);
                            }else{
                                flyOtherDisable(target,player,username);
                            }
                        }else{
                            player.sendMessage(Lang.TITLE.toString() + Lang.NO_ONLINE);
                        }
                    }else{
                        player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
                    }
                }else{
                    if(!this.flyEnabled.containsKey(player.getUniqueId())){
                        flyEnable(player);
                    }else{
                        flyDisable(player);
                    }
                }
            }else{
                player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
            }

            //prueba
        }else if(sender instanceof ConsoleCommandSender){
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if(args.length != 0){
                if(console.hasPermission("dripperscore.fly.others")){
                    String username = args[0];
                    Player target = Bukkit.getServer().getPlayerExact(username);
                    if(target!= null){

                        if(!this.flyEnabled.containsKey(target.getUniqueId())){
                            target.setAllowFlight(true);
                            flyEnabled.put(target.getUniqueId(),true);
                            target.sendMessage(Lang.TITLE.toString() + Lang.FLY_ENABLED);
                            console.sendMessage(Lang.TITLE.toString() + Lang.FLY_OTHER_ENABLED.toString().replace("%t", target.getName()));
                        }else{
                            target.setAllowFlight(false);
                            flyEnabled.remove(target.getUniqueId());
                            target.sendMessage(Lang.TITLE.toString() + Lang.FLY_DISABLED);
                            console.sendMessage(Lang.TITLE.toString() + Lang.FLY_OTHER_DISABLED.toString().replace("%t", target.getName()));
                        }
                    }else{
                        console.sendMessage(Lang.TITLE.toString() + Lang.NO_ONLINE);
                    }
                }else{
                    console.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
                }
            }else{
                console.sendMessage(Lang.TITLE.toString() + Lang.INVALID_ARGS);
            }
        }
        return true;
    }

    private void flyEnable(Player player){
        player.setAllowFlight(true);
        flyEnabled.put(player.getUniqueId(),true);
        player.sendMessage(Lang.TITLE.toString() + Lang.FLY_ENABLED);
    }
    private void flyDisable(Player player){
        player.setAllowFlight(false);
        flyEnabled.remove(player.getUniqueId());
        player.sendMessage(Lang.TITLE.toString() + Lang.FLY_DISABLED);
    }
    private void flyOtherEnable(Player target, Player player, String username){
        target.setAllowFlight(true);
        flyEnabled.put(target.getUniqueId(),true);
        target.sendMessage(Lang.TITLE.toString() + Lang.FLY_ENABLED);
        player.sendMessage(Lang.TITLE.toString() + Lang.FLY_OTHER_ENABLED.toString().replace("%t", target.getName()));
    }
    private void flyOtherDisable(Player target, Player player, String username){
        target.setAllowFlight(false);
        flyEnabled.remove(target.getUniqueId());
        target.sendMessage(Lang.TITLE.toString() + Lang.FLY_DISABLED);
        player.sendMessage(Lang.TITLE.toString() + Lang.FLY_OTHER_DISABLED.toString().replace("%t", target.getName()));
    }
}
