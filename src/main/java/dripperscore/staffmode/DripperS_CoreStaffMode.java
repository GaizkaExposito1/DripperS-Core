package dripperscore.staffmode;

import dripperscore.DripperS_Core;
import dripperscore.DripperS_CoreModule;
import lombok.Getter;

public class DripperS_CoreStaffMode implements DripperS_CoreModule {
    public static final String MODULE_NAME = "StaffMode";

    @Getter
    private static DripperS_CoreStaffMode instance;
    @Getter
    private StaffModeConfig staffModeConfig;
    @Getter
    private CommandManager commandManager;
    @Getter
    private final DripperS_Core core;


    public DripperS_CoreStaffMode(DripperS_Core dripperSCore) {
        instance = this;
        this.core = dripperSCore;
    }

    @Override
    public void enable() {
        core.printOnEnableMessage("staffMode");
    }

    @Override
    public void disable() {
        core.printOnDisableMessage("staffMode");
    }

    @Override
    public void reload() {
        this.staffModeConfig.reload();
        this.commandManager.enableCommands();
    }

}
