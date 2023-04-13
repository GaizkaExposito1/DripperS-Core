package dripperscore.staffmode;

import dripperscore.DripperS_Core;
import dripperscore.DripperS_CoreModule;
import dripperscore.FileManagerConfig;
import org.bukkit.configuration.file.YamlConfiguration;

public class StaffModeConfig {

    //private final FileManagerConfig.Config config;
    private final DripperS_Core plugin;

    public StaffModeConfig(DripperS_Core plugin) {
        this.plugin = plugin;
        //this.config = this.plugin.getConfigFile();
    }



    public void reload() {
        this.plugin.reloadConfig();
    }
}
