package dripperscore.staff.commands;

import dripperscore.DripperS_Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class banGuiCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            ArrayList<Player> PlayerList = new ArrayList<>(p.getServer().getOnlinePlayers());

            Inventory bangui = Bukkit.createInventory(p,45, ChatColor.BLUE + "Player List");

            for(int i = 0; i< PlayerList.size(); i++ ){
                ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1);
                ItemMeta meta  = playerHead.getItemMeta();

                meta.setDisplayName(ChatColor.GRAY+ PlayerList.get(i).getDisplayName());
                ArrayList<String>lore = new ArrayList<>();
                lore.add(ChatColor.GOLD + "Player Helth: "+ PlayerList.get(i).getHealth());
                lore.add(ChatColor.GOLD + "Food Level: "+ PlayerList.get(i).getFoodLevel());
                lore.add(ChatColor.GOLD + "EXP: "+ PlayerList.get(i).getExp());
                lore.add(ChatColor.GOLD + "Played Time: "+ PlayerList.get(i).getPlayerTime());
                meta.setLore(lore);
                playerHead.setItemMeta(meta);

                bangui.addItem(playerHead);
            }
            p.openInventory(bangui);
        }else{
            
        }

        return true;
    }
}
