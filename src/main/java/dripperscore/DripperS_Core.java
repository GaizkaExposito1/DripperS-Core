package dripperscore;


import dripperscore.lang.Lang;
import dripperscore.staff.commands.freezeCommand;
import dripperscore.staff.commands.vanishCommand;
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
        printOnEnableMessage("global");
        getConfigFile();
        loadLang();
        enableCommands();
        createBBDDConnection();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        printOnDisableMessage("global");
    }

    public void createBBDDConnection(){
        if(getConfig().getString("sqlConnection").equalsIgnoreCase("true")){
            /*
             * DARLE UNA PENSADA
             * DARLE UNA PENSADA
             * DARLE UNA PENSADA
             * DARLE UNA PENSADA
             * DARLE UNA PENSADA
             * DARLE UNA PENSADA
             */
            String url = "jdbc:"+getConfig().getString("sqlType")+"://"+getConfig().getString("sqlUrl")+"/"+getConfig().getString("sqlBBDDName");
            String user = getConfig().getString("sqlUser");
            String password = getConfig().getString("sqlPass");

            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                this.getLogger().info("DripperS-Core --> SQL Connected");

            }catch (SQLException e){
                e.printStackTrace();
                this.getLogger().info("DripperS-Core --> Error connecting SQL");
                this.getLogger().info(e.toString());
            }
        }else{
            this.getLogger().info("DripperS-Core --> Enabling WithOut SQL Connection");

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
    public void enableCommands(){
        getCommand("dfreeze").setExecutor(new freezeCommand(this));
        getCommand("dvanish").setExecutor(new vanishCommand(this,"vanish"));
        getCommand("dvlist").setExecutor(new vanishCommand(this,"vanishlist"));
    }

    public void printOnEnableMessage(String module) {
        try {
            if(module.equalsIgnoreCase("staffMode")){
                this.getLogger().info("DripperS-Core --> StaffMode");
                this.getLogger().info("Successfully Enabled");
            } else if (module.equalsIgnoreCase("stats")) {
                this.getLogger().info("DripperS-Core --> Stats");
                this.getLogger().info("Successfully Enabled");
            }else if(module.equalsIgnoreCase("global")){
                this.getLogger().info("DripperS-Core ");
                this.getLogger().info("Successfully Enabled");
            }
        } catch (Exception ignored) {
            //Añadir excepcion
        }
    }
    private void getConfigFile(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public void printOnDisableMessage(String module){
        try {
            if(module.equalsIgnoreCase("staffMode")){
                this.getLogger().info("DripperS-Core --> StaffMode");
                this.getLogger().info("Successfully Disabled");
            } else if (module.equalsIgnoreCase("stats")) {
                this.getLogger().info("DripperS-Core --> Stats");
                this.getLogger().info("Successfully Disabled");
            }else if(module.equalsIgnoreCase("global")){
                this.getLogger().info("DripperS-Core");
                this.getLogger().info("Successfully Disabled");
            }
        } catch (Exception ignored) {
            //Añadir excepcion
        }
    }
}
