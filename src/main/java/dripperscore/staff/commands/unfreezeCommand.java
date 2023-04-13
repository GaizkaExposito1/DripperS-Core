package dripperscore.staff.commands;

import dripperscore.DripperS_Core;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class unfreezeCommand implements CommandExecutor {
    @Getter
    private final DripperS_Core core;
    private boolean Freezed;

    public unfreezeCommand(DripperS_Core plugin) {
        this.core = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args ) {
        String unFreezedMenssage = core.getConfig().getString("PlayerUnFreezedText");
        String notPermissons = core.getConfig().getString("notPermissonsText");

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("dripperscore.*") ||player.hasPermission("dripperscore.staffmode.*")
                    ||player.hasPermission("dripperscore.staffmode.freeze") ||player.hasPermission("dripperscore.staffmode.unfreeze")  ){
                if(args.length == 0){
                    player.sendMessage(ChatColor.RED+"You did not provide any arguments when running the command");
                    player. sendMessage(ChatColor.RED+"Example: /dunfreeze <payer>");
                }else{
                    String username = args[0];
                    Player target =Bukkit.getServer().getPlayerExact(username);

                    if(target == null){
                        player.sendMessage(ChatColor.RED+"This player is not online");
                    }else{
                        unFreeze(target, player,username);
                        target.sendMessage(ChatColor.GREEN + unFreezedMenssage);
                    }

                }
            }else{
                player.sendMessage(ChatColor.RED+notPermissons);
            }
        }else{
            Bukkit.getLogger().info(command + "can only execute being a player");
        }

        return true;
    }
    private void unFreeze(Player target, Player player,String username){
        target.setWalkSpeed(0.2f);
        target.removePotionEffect(PotionEffectType.JUMP);
        target.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(ChatColor.GREEN +username + "is being Unfreezed");
    }

}
