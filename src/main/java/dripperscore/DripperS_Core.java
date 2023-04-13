package dripperscore;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class DripperS_Core extends JavaPlugin {


    @Override
    public void onEnable() {
        // Plugin startup logic
        printOnEnableMessage();
        getConfigFile();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    private void printOnEnableMessage() {
        try {
            this.getLogger().info("DripperS-Core");
            this.getLogger().info(this.getDescription().getVersion());
            this.getLogger().info("By: " + this.getDescription().getAuthors());
            //this.getLogger().info("Website: " + this.getDescription().getWebsite());
        } catch (Exception ignored) {
        }
    }
    private void getConfigFile(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
}
