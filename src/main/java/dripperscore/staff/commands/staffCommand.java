package dripperscore.staff.commands;

import dripperscore.DripperS_Core;
import dripperscore.lang.Lang;
import dripperscore.staff.inventories.staffInventory;
import lombok.Getter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class staffCommand implements CommandExecutor {
    @Getter
    private final DripperS_Core core;

    public staffCommand(DripperS_Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof  Player){
            Player player = (Player) sender ;
            staffInventory gui = new staffInventory();
            player.openInventory(gui.getInventory());
        }
        //
        //Inventory oldInventory = Bukkit.createInventory(player, 54, ChatColor.RED+"StaffMenu");
        //oldInventory.getContents();
        //Inventory staffInventory = Bukkit.createInventory(player, 9, ChatColor.RED+"StaffMenu");





        //if(sender instanceof Player){
        //    Player player = (Player) sender;
        //    player.sendMessage(  Lang.TITLE +"This command is currently creating");
        //}
        //player.inve
        return true;
    }
}
