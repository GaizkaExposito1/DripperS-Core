package dripperscore.staffmode;

import dripperscore.DripperS_Core;
import lombok.Getter;

public class CommandManager {
    @Getter
    private final DripperS_Core plugin;
    //private final Set<TokensCommand> commands;

    public CommandManager(DripperS_Core plugin) {
        this.plugin = plugin;
        //this.commands = new HashSet<>();
    }
    public void enableListeners(){

    }
    public void enableCommands(){
        //plugin.getCommand("prueba").setExecutor(new PruebaCommand(this));
    }


}
