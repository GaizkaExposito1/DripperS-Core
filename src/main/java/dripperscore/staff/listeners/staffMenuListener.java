package dripperscore.staff.listeners;

import dripperscore.staff.inventories.staffInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class staffMenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null){
            return;
        }
        if(e.getClickedInventory().getHolder() instanceof staffInventory){
            if(e.isLeftClick()){
                if(e.getCurrentItem().getType() == Material.BARRIER){
                    Player player = (Player) e.getWhoClicked();
                    player.sendMessage("Ha clickado el kick event.");
                }
            }
            e.setCancelled(true);
        }


    }
}
