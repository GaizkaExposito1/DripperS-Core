package dripperscore;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DatabaseManager {
    String type;
    String url;
    String port;
    String DDBBName;
    String username;
    String password;

    @Getter
    private final DripperS_Core core;

    public DatabaseManager(DripperS_Core core) {
        this.core = core;
    }


    public void createBBDDConnection(String type,String url,String port,String DDBBName,String username,String password){
        this.type = type;
        this.url = url;
        this.port = port;
        this.DDBBName = DDBBName;
        this.username = username;
        this.password = password;

        PreparedStatement statement = null;

        if(type.equalsIgnoreCase("MySql")){
            String conectionUrl = "jdbc:"+this.type+"://"+this.url+":"+ this.port+"/??useUnicode=true&characterEncoding=UTF-8";

            try{
                Connection connection = DriverManager.getConnection(conectionUrl,this.username,this.password);

                statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS "+this.DDBBName);
                statement.executeUpdate();

                statement = connection.prepareStatement("USE "+ this.DDBBName);
                statement.executeUpdate();

                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.GREEN+"SQL Connected");

            }catch (SQLException e){
                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error connecting SQL");
                core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.YELLOW+"Enabling WithOut SQL Connection");
            }
        }
    }
}

