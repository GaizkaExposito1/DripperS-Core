package dripperscore;
import dripperscore.entities.User;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.UUID;

public class DatabaseManager {
    String type;
    String url;
    String port;
    String DDBBName;
    String username;
    String password;

    UUID uuid;
    @Getter
    private final DripperS_Core core;
    Connection connection;

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
                connection = DriverManager.getConnection(conectionUrl,this.username,this.password);

                statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS "+this.DDBBName);
                statement.executeUpdate();

                statement = connection.prepareStatement("USE "+ this.DDBBName);
                statement.executeUpdate();


                createTableUsers();

                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.GREEN+"SQL Connected");

            }catch (SQLException e){
                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error connecting SQL");
                core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
                core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.YELLOW+"Enabling WithOut SQL Connection");
            }
        }
    }
    public void createTableUsers(){
        try (
             PreparedStatement stmt = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS users (" +
                             "uuid BINARY(16) NOT NULL," +
                             "username VARCHAR(255) NOT NULL," +
                             "last_login DATETIME NOT NULL," +
                             "first_ip VARCHAR(45) NOT NULL," +
                             "last_ip VARCHAR(45) NOT NULL," +
                             "PRIMARY KEY (uuid)" +
                             ")"
             )
        ) {
            stmt.execute();
            System.out.println("Tabla 'users' creada exitosamente.");
        } catch (SQLException ex) {
            System.err.println("Error al crear tabla 'users': " + ex.getMessage());
        }
    }
    public User GetUserByUUID(UUID uuid){
        this.uuid = uuid;
        PreparedStatement statement = null;
         try {
             statement = connection.prepareStatement("Select * from users where uuid = '"+uuid+"'");
             statement.executeQuery();



         }catch (SQLException e){
             core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error searching user");
             core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
         }

        return null;
    }
    public void SaveUser(User user){
        PreparedStatement statement = null;

        try {
            /*if (user != null) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE INTO users (username, lastLogin, lastIP) VALUES (?, ?, ?)");
                //stmt.setString(1, user.getUUID());
                stmt.setString(2, user.getUsername());
                stmt.setTimestamp(3, Timestamp.from(user.getLastLogin().toInstant(ZoneOffset.UTC)));
                stmt.setString(4, user.getLastIp());
                //stmt.setString(5, user.getFirstIP());
                stmt.executeUpdate();




            }else{*/
                PreparedStatement stmt = connection.prepareStatement("INSERT OR UPDATE INTO users (uuid, username, lastLogin, lastIP, firstIP) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1, String.valueOf(user.getUuid()));
                stmt.setString(2, user.getUsername());
                stmt.setTimestamp(3, Timestamp.from(user.getLastLogin().toInstant(ZoneOffset.UTC)));
                stmt.setString(4, user.getLastIp());
                stmt.setString(5, user.getFirstIp());
                stmt.executeUpdate();

            //}




        }catch (SQLException e){
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error saving user");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
        }

    }



    public void closeConnection(){
        try{
            connection.close();
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.YELLOW+"SQL Disconected");
        }catch (SQLException e){
            core.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"DripperS-Core: "+ChatColor.RED+"Error Disconnecting SQL");
            core.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+e.toString());
        }
    }
}

