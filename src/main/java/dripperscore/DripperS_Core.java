package dripperscore;


import dripperscore.lang.Lang;
import dripperscore.other.commands.flyCommand;
import dripperscore.staff.commands.freezeCommand;
import dripperscore.staff.commands.staffCommand;
import dripperscore.staff.commands.vanishCommand;
import dripperscore.staff.listeners.staffMenuListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DripperS_Core extends JavaPlugin {
    public static YamlConfiguration LANG;
    public static File LANG_FILE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfigFile();
        loadLang();
        enableListeners();
        createBBDDConnection();
        enableCommands();
        printOnEnableMessage("global");
    }
    public void enableCommands(){
        getCommand("dfreeze").setExecutor(new freezeCommand(this));
        getCommand("dvanish").setExecutor(new vanishCommand(this,"vanish"));
        getCommand("dvlist").setExecutor(new vanishCommand(this,"vanishlist"));
        getCommand("dstaff").setExecutor(new staffCommand(this));
        getCommand("dfly").setExecutor(new flyCommand(this));
    }
    public void enableListeners(){
        getServer().getPluginManager().registerEvents(new staffMenuListener(), this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        printOnDisableMessage("global");
    }

    public void createBBDDConnection(){

        if(getConfig().getString("sqlConnection").equalsIgnoreCase("true")){

            String type = getConfig().getString("sqlType");
            String url = getConfig().getString("sqlUrl");
            String port = getConfig().getString("sqlPort");
            String databaseName = getConfig().getString("sqlBBDDName");
            String user = getConfig().getString("sqlUser");
            String password = getConfig().getString("sqlPass");
            DatabaseManager dbManager = new DatabaseManager(this);
            dbManager.createBBDDConnection(type,url,port,databaseName,user,password);
        }else{
            this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.YELLOW+"Enabling WithOut SQL Connection");
        }
    }
    /**
     * Load the lang.yml file.
     * @return The lang.yml config.
     */
    public void loadLang() {
        File lang = new File(getDataFolder(), "lang.yml");
        if (!lang.exists()) {
            try {
                getDataFolder().mkdir();
                lang.createNewFile();
                InputStream defConfigStream = this.getResource("lang.yml");
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    defConfig.save(lang);
                    Lang.setFile(defConfig);
                }
            } catch(IOException e) {
                e.printStackTrace(); // So they notice
                this.getLogger().info("DripperS-Core --> Couldn't create language file.");
                this.getLogger().info("DripperS-Core --> This is a fatal error. Now disabling.");
                this.setEnabled(false); // Without it loaded, we can't send them messages
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for(Lang item:Lang.values()) {
            if(conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(conf);
        DripperS_Core.LANG = conf;
        DripperS_Core.LANG_FILE = lang;
        try {
            conf.save(getLangFile());
        } catch(IOException e) {
            this.getLogger().info("DripperS-Core --> Failed to save lang.yml.");
            this.getLogger().info("DripperS-Core --> Report this stack trace to <your name>.");
            e.printStackTrace();
        }
    }
    /**
     * Gets the lang.yml config.
     * @return The lang.yml config.
     */
    public YamlConfiguration getLang() {
        return LANG;
    }

    /**
     * Get the lang.yml file.
     * @return The lang.yml file.
     */
    public File getLangFile() {
        return LANG_FILE;
    }
    public void printOnEnableMessage(String module) {
        try {
            if(module.equalsIgnoreCase("staffMode")){
                this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core (staff): "+ChatColor.GREEN+"Successfully Enabled");
            } else if (module.equalsIgnoreCase("stats")) {
                this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core (stats): "+ChatColor.GREEN+"Successfully Enabled");
            }else if(module.equalsIgnoreCase("global")){
                //usar el getserver.getconsolesender.sendmessage para poner los colores :)
                this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.GREEN+"Successfully Enabled");
            }
        } catch (Exception e) {
            this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error enabling the plugin");
            this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+e);
        }
    }
    private void getConfigFile(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public void printOnDisableMessage(String module){
        try {
            if(module.equalsIgnoreCase("staffMode")){
                this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core (staff): "+ChatColor.YELLOW+"Successfully Disabled");
            } else if (module.equalsIgnoreCase("stats")) {
                this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core (stats): "+ChatColor.YELLOW+"Successfully Disabled");
            }else if(module.equalsIgnoreCase("global")){
                this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.YELLOW+"Successfully Disabled");
            }
        } catch (Exception e) {
            this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error disabling the plugin");
            this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+e);
        }
    }
}
