package dripperscore;


import org.bukkit.plugin.java.JavaPlugin;



public final class DripperS_Core extends JavaPlugin {



    @Override
    public void onEnable() {
        // Plugin startup logic
        printOnEnableMessage("global");
        getConfigFile();
        enableCommands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        printOnDisableMessage("global");
    }


    public void enableCommands(){
        //getCommand("dfreeze").setExecutor(new Freeze());
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
