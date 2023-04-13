package dripperscore.staff.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class staffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender ;
        Inventory oldInventory = Bukkit.createInventory(player, 54, ChatColor.RED+"StaffMenu");
        oldInventory.getContents();
        Inventory staffInventory = Bukkit.createInventory(player, 9, ChatColor.RED+"StaffMenu");

        //player.inve
        return true;
    }
}
