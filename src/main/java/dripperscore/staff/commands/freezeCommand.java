package dripperscore.staff.commands;

import dripperscore.DripperS_Core;
import dripperscore.lang.Lang;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class freezeCommand implements CommandExecutor {
    @Getter
    private final DripperS_Core core;
    private final HashMap<UUID,Boolean> freezed;

    public freezeCommand(DripperS_Core plugin) {
        this.core = plugin;
        freezed = new HashMap<>();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args ) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("dripperscore.*") ||player.hasPermission("dripperscore.staffmode.*")
                    ||player.hasPermission("dripperscore.staffmode.freeze") ||player.hasPermission("dripperscore.staffmode.unfreeze")  ){
                if(args.length == 0){
                    player.sendMessage(Lang.TITLE.toString() + Lang.INVALID_ARGS);
                    player. sendMessage(Lang.TITLE.toString() + Lang.COMMAND_EXAMPLE + "/dfreeze <player>");
                }else{
                    String username = args[0];
                    Player target =Bukkit.getServer().getPlayerExact(username);

                    if(target == null){
                        player.sendMessage(Lang.TITLE.toString() + Lang.NO_ONLINE);
                    }else{
                        if(player != target){
                            if(!this.freezed.containsKey(player.getUniqueId())){
                                this.freezed.put(player.getUniqueId(),true);
                                Freeze(target, player, username);
                                target.sendMessage(Lang.TITLE.toString() + Lang.TARGET_FREEZED);
                            }else{
                                this.freezed.remove(player.getUniqueId());
                                unFreeze(target, player,username);
                                target.sendMessage(Lang.TITLE.toString() + Lang.TARGET_UNFREEZED);
                            }
                        }else{
                            player.sendMessage(Lang.TITLE.toString() + Lang.NO_SELF_FREEZED);


                            if(!this.freezed.containsKey(player.getUniqueId())){
                                this.freezed.put(player.getUniqueId(),true);
                                Freeze(target, player, username);
                                target.sendMessage(Lang.TITLE.toString() + Lang.TARGET_FREEZED);
                            }else{
                                this.freezed.remove(player.getUniqueId());
                                unFreeze(target, player,username);
                                target.sendMessage(Lang.TITLE.toString() + Lang.TARGET_UNFREEZED);
                            }
                        }
                    }
                }
            }else{
                player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
            }
        }else{
            Bukkit.getLogger().info(Lang.TITLE.toString() + Lang.PLAYER_ONLY);
        }
        return true;
    }

    private void Freeze(Player target, Player player,String username){
        target.setWalkSpeed(0);
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 200));
        target.setGameMode(GameMode.ADVENTURE);
        player.sendMessage(  Lang.TITLE.toString() + Lang.PLAYER_FREEZED.toString().replace("%t", target.getName()));
    }
    private void unFreeze(Player target, Player player,String username){
        target.setWalkSpeed(0.2f);
        target.removePotionEffect(PotionEffectType.JUMP);
        target.removePotionEffect(PotionEffectType.SLOW);
        target.setGameMode(GameMode.SURVIVAL);
        player.sendMessage( Lang.TITLE.toString() + Lang.PLAYER_UNFREEZED.toString().replace("%t", target.getName()));
    }
}