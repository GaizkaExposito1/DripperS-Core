package dripperscore.staff.inventories;

import dripperscore.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class staffInventory implements InventoryHolder {
    private Inventory inv;
    public staffInventory(){
        inv = Bukkit.createInventory(this,27, Lang.STAFF_MENU_TITLE.toString());
        init();
        //inv.setItem(10,Prueba);
    }
    private void init(){
        //ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1);
        ItemStack item;
        List<String> lore = new ArrayList<>();
        lore.add("Prueba1");
        lore.add("Prueba2");
        lore.add("Prueba3");
        item = createItem("Kick",Material.BARRIER,Collections.singletonList("Kickeas a alquien"));
        inv.setItem(10,item);
        item = createItem("Prueba2",Material.BOOK,lore);
        inv.setItem(12,item);

        //item = createItem();
    }
    private ItemStack createItem(String name, Material mat, List<String> lore){
        ItemStack item = new ItemStack(mat,1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);


        return item;

    }
    @Override
    public Inventory getInventory() {
        return inv;
    }
}
