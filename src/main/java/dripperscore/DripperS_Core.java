package dripperscore;


import dripperscore.staff.commands.freezeCommand;
import dripperscore.staff.commands.unfreezeCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DripperS_Core extends JavaPlugin {



    @Override
    public void onEnable() {
        // Plugin startup logic
        printOnEnableMessage("global");
        getConfigFile();
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
            /**
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
    public void enableCommands(){
        getCommand("dfreeze").setExecutor(new freezeCommand(this));
        getCommand("dunfreeze").setExecutor(new unfreezeCommand(this));
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
        }
    }

}
