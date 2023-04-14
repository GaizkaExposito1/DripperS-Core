package dripperscore.lang;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enum for requesting strings from the language file.
 * @author gomeow
 */
public enum Lang {
    TITLE("title-name", "&3DripperS-Core : "),
    INVALID_ARGS("invalid-args", "You did not provide any arguments when running the command."),
    PLAYER_ONLY("player-only", "Sorry but that can only be run by a player!"),
    MUST_BE_NUMBER("must-be-number", "&cYou need to specify a number, not a word."),
    NO_PERMS("no-permissions", "&cYou don't have permission for that!"),
    NO_ONLINE("no-online", "&cThe player is not online!"),
    COMMAND_EXAMPLE("command_example", "&6Example : "),
    NO_SELF_FREEZED("staff-no-self-freezed", "&cYou cant freeze Yourself."),
    PLAYER_FREEZED("staff-player-freezed", "&9%t is being freezed."),
    TARGET_FREEZED("staff-target-freezed", "&9You have been frozen."),
    PLAYER_UNFREEZED("staff-player-unfreezed", "&2%t is being unfreezed."),
    TARGET_UNFREEZED("staff-target-unfreezed", "&2You have been unfrozen."),
    STAFF_VANISHED("staff-vanished", "&5You now are invisible to others."),
    STAFF_UNVANISHED("staff-unvanished", "&2You now are visible to others."),
    PLAYER_VANISHED("staff-player-vanished", "&5%t is being invisible to others."),
    PLAYER_UNVANISHED("staff-player-unvanished", "&2%t is being visible to others."),
    STAFF_VANISH_LIST("staff-vanis-list", "&dStaff in vanish: ");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
     * Lang enum constructor.
     * @param path The string path.
     * @param start The default string.
     */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        if (this == TITLE)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }
}
