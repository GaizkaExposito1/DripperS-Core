package dripperscore.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID uuid;
    private  String username;
    private LocalDateTime lastLogin;
    private  String lastIp;
    private  String firstIp;
    public User(UUID uuid,String username,LocalDateTime lastLogin,String lastIp,String firstIp){
        this.uuid = uuid;
        this.username = username;
        this.lastLogin = lastLogin;
        this.lastIp = lastIp;
        this.firstIp = firstIp;

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getFirstIp() {
        return firstIp;
    }

    public void setFirstIp(String firstIp) {
        this.firstIp = firstIp;
    }
}
